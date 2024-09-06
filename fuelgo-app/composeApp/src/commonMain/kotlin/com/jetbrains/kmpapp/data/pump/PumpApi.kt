package com.jetbrains.kmpapp.data.pump

import com.jetbrains.kmpapp.data.EnvVars
import com.jetbrains.kmpapp.screens.map.LocationProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.sse.sse
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException


interface PumpApi {

    suspend fun getGasStations(): List<GasStationObject>
    suspend fun getGasStationById(stationId: Int): GasStationObject
    suspend fun getPumps(stationId: Int): List<PumpObject>
    suspend fun getPumpById(stationId: Int, pumpId: Int): PumpObject?
    suspend fun getPetrolTypes(stationId: Int, objectId: Int): List<PetrolObject?>
    suspend fun watchFilling(pumpId: Int, petrolId: Int, eventProcessor: (PetrolStatus) -> Unit)
}

class KtorPumpApi(
    private val client: HttpClient,
    private val sseClient: HttpClient,
    private val envVar: EnvVars,
    private val locationProvider: LocationProvider
) : PumpApi {

    override suspend fun getGasStations(): List<GasStationObject> {
        val location = locationProvider.getCurrentLocation().value;
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/map/station")
                parameters.append("lat", location.latitude.toString())
                parameters.append("lon", location.longitude.toString())
            }
        }

        val json: String = response.body()
        println("json $json")

        return response.body()
    }

    override suspend fun getGasStationById(stationId: Int): GasStationObject {
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/map/station/$stationId")
            }
        }

        val json: String = response.body()
        println("json $json")

        return response.body()
    }

    override suspend fun getPumps(stationId: Int): List<PumpObject> {
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/station/$stationId/pumps/")
            }
        }

        val json: String = response.body()
        println("json pump $json")

        return response.body()
    }

    override suspend fun getPumpById(stationId: Int, pumpId: Int): PumpObject? {
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/station/$stationId/pumps/$pumpId")
            }
        }

        val json: String = response.body()
        println("json $json")

        return response.body()
    }

    override suspend fun getPetrolTypes(stationId: Int, objectId: Int): List<PetrolObject?> {
        val response = client.get(envVar.backendUrl) {
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
            url {
                host = envVar.backendUrl
                port = envVar.port
                path("/station/$stationId/pumps/$objectId/fuel-type")
            }
        }

        val json: String = response.body()
        println("json $json")


        return response.body()
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
}
