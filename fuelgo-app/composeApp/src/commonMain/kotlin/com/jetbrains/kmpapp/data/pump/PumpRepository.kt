package com.jetbrains.kmpapp.data.pump

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PumpRepository(
    private val pumpApi: PumpApi,
//    private val pumpStorage: PumpStorage
) {

    private val scope = CoroutineScope(SupervisorJob())

//    fun initialize() {
//        scope.launch {
//            refresh()
//        }
//    }
//
//    // TODO: on startup
//    suspend fun refresh() {
//        pumpStorage.saveObjects(pumpApi.getData())
//    }

//    fun getObjects(): Flow<List<PumpObject>> = pumpStorage.getObjects()


    fun getGasStations(): MutableStateFlow<List<GasStationObject?>> {
        val result = MutableStateFlow<List<GasStationObject?>>(listOf())
        scope.launch {
            result.value = pumpApi.getGasStations()
        }
        return result
    }

    fun getGasStationById(stationId: Int): MutableStateFlow<GasStationObject?> {
        val result = MutableStateFlow<GasStationObject?>(null)
        scope.launch {
            result.value = pumpApi.getGasStationById(stationId)
        }
        return result
    }

    fun getPumps(stationId: Int): MutableStateFlow<List<PumpObject?>> {
        val result = MutableStateFlow<List<PumpObject?>>(ArrayList())
        scope.launch {
            result.value = pumpApi.getPumps(stationId)
        }
        return result
    }

    fun getPumpById(stationId: Int, objectId: Int): MutableStateFlow<PumpObject?> {
        val result = MutableStateFlow<PumpObject?>(null)
        scope.launch {
            result.value = pumpApi.getPumpById(stationId, objectId)
        }
        return result
    }

    fun getPetrolTypes(stationId: Int, objectId: Int): MutableStateFlow<List<PetrolObject?>> {
        val result = MutableStateFlow<List<PetrolObject?>>(emptyList())
        scope.launch {
            result.value = pumpApi.getPetrolTypes(stationId, objectId)
        }
        return result
    }

    fun getPumpStatus(
        pumpId: Int,
        petrolId: Int,
        onFinished: () -> Unit = {}
    ): MutableStateFlow<Float> {
        val status = MutableStateFlow(0F)
        scope.launch {
            delay(2000L)
            pumpApi.watchFilling(pumpId, petrolId) { event ->
                println(">> filling $event")
                status.value = event.diff

                if (event.event == 3) {
                    println(">> Closed by ${event.event}")
                    onFinished()
                }
            }
        }

        return status;
    }
}