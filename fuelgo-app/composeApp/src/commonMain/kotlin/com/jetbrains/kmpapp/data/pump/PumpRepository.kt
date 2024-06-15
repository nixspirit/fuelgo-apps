package com.jetbrains.kmpapp.data.pump

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PumpRepository(
    private val pumpApi: PumpApi,
    private val pumpStorage: PumpStorage
) {

    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    // TODO: on startup
    suspend fun refresh() {
        pumpStorage.saveObjects(pumpApi.getData())
    }

    fun getObjects(): Flow<List<PumpObject>> = pumpStorage.getObjects()
    fun getPetrolTypes(objectId: Int): MutableStateFlow<List<PetrolObject?>> {
        return MutableStateFlow(pumpApi.getPetrolTypes(objectId))
    }

    fun getObjectById(objectId: Int): Flow<PumpObject?> = pumpStorage.getObjectById(objectId)
    fun getPumpStatus(pumpId: Int, petrolId: Int): MutableStateFlow<Float> {
        val status = MutableStateFlow<Float>(0F)
        scope.launch {
            delay(2000L)
            pumpApi.watchFilling(pumpId, petrolId) { event ->
                println("Process event " + event)
                status.value += 0.1F
            }
        }

        return status;
    }
}