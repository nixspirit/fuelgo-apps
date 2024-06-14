package com.jetbrains.kmpapp.data.pump

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface PumpStorage {
    suspend fun saveObjects(newObjects: List<PumpObject>)

    fun getObjectById(objectId: Int): Flow<PumpObject?>

    fun getObjects(): Flow<List<PumpObject>>
}

class InMemoryPumpStorage : PumpStorage {
    private val storedObjects = MutableStateFlow(emptyList<PumpObject>())

    override suspend fun saveObjects(newObjects: List<PumpObject>) {
        storedObjects.value = newObjects
    }

    override fun getObjectById(objectId: Int): Flow<PumpObject?> {
        return storedObjects.map { objects ->
            objects.find { it.objectID == objectId }
        }
    }

    override fun getObjects(): Flow<List<PumpObject>> = storedObjects
}
