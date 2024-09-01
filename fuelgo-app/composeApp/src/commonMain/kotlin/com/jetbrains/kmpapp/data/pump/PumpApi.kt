package com.jetbrains.kmpapp.data.pump

import com.jetbrains.kmpapp.data.EnvVars
import com.jetbrains.kmpapp.screens.map.LocationProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.sse.sse
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException


interface PumpApi {

    suspend fun getData(): List<PumpObject>

    fun getPetrolTypes(objectId: Int): List<PetrolObject?>

    suspend fun watchFilling(pumpId: Int, petrolId: Int, eventProcessor: (PetrolStatus) -> Unit)

    suspend fun getGasStations(): List<GasStationObject>
}

class KtorPumpApi(
    private val client: HttpClient,
    private val sseClient: HttpClient,
    private val envVar: EnvVars,
    private val locationProvider: LocationProvider
) : PumpApi {


    override suspend fun getData(): List<PumpObject> {
        return try {
            // client.get(API_URL).body()

            return listOf(
                PumpObject(1, "1"),
                PumpObject(2, "2"),
                PumpObject(3, "3"),
                PumpObject(4, "4"),
                PumpObject(5, "5"),
                PumpObject(6, "6")
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            emptyList()
        }
    }

    override fun getPetrolTypes(objectId: Int): List<PetrolObject?> {
        return try {
            // client.get(API_URL).body()

            return listOf(
                PetrolObject(10, "E5"),
                PetrolObject(20, "E10"),
            )
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            emptyList()
        }
    }

    override suspend fun watchFilling(
        pumpId: Int,
        petrolId: Int,
        eventProcessor: (PetrolStatus) -> Unit
    ) {
        sseClient.sse(host = envVar.backendUrl, port = envVar.port, path = "/events") {
            while (true) {
                incoming.collect { event ->
                    println("Event from server: " + event.data)

                    val jsonString: PetrolStatus? =
                        event.data?.let { Json.decodeFromString<PetrolStatus>(it) }
                    println("OBJ from JSON $jsonString")

                    if (jsonString != null) {
                        eventProcessor(jsonString)
                    }
                }
            }
        }


    }

    override suspend fun getGasStations(): List<GasStationObject> {
        val location = locationProvider.getCurrentLocation().value;
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/map/station/${location.latitude}/${location.longitude}")
            }
        }

        val json: String = response.body()
        println("json $json")

        return response.body()
    }
}
