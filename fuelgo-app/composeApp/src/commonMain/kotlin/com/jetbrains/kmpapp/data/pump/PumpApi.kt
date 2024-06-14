package com.jetbrains.kmpapp.data.pump

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException


interface PumpApi {
    suspend fun getData(): List<PumpObject>
    fun getPetrolTypes(objectId: Int): List<PetrolObject?>
}

class KtorPumpApi(private val client: HttpClient) : PumpApi {
    companion object {
        private const val API_URL = ""
    }

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
}
