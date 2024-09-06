package com.jetbrains.kmpapp.integrational

import com.jetbrains.kmpapp.data.pump.PumpRepository
import com.jetbrains.kmpapp.di.dataModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GasStationApiTest : KoinTest {

    val pumpApi: PumpRepository by inject()

    @Test
    fun `should return a list of gas stations`() = runBlocking {
        startKoin {
            modules(dataModule)
        }
        val gasStations = pumpApi.getGasStations()
        delay(2000)

        println(gasStations.value)
        assertEquals(2, gasStations.value.size)
        assertNotNull(gasStations.value[0])
        assertNotNull(gasStations.value[1])

        assertEquals(10, gasStations.value[0]?.objectID)
        assertEquals("Shell", gasStations.value[0]?.title)

        assertEquals(12, gasStations.value[1]?.objectID)
        assertEquals("Total", gasStations.value[1]?.title)
    }

    @Test
    fun `should return a gas station by its id`() = runBlocking {
        startKoin {
            modules(dataModule)
        }
        val gasStations = pumpApi.getGasStationById(10)
        delay(2000)

        println(gasStations.value)
        assertNotNull(gasStations.value)

        assertEquals(10, gasStations.value?.objectID)
        assertEquals("Shell", gasStations.value?.title)

    }
}