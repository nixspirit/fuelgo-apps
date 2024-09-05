package com.jetbrains.kmpapp.data.pump

import com.jetbrains.kmpapp.data.EnvVars
import com.jetbrains.kmpapp.screens.map.LocationProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GasStationApiMockedTest : KoinTest {

    val pumpApi: PumpRepository by inject()


    @Test
    fun `should return a list of gas stations`() = runBlocking {
        registerHttpResponseMocks()

        startKoin {
            val httpClientMock = declareMock<HttpClient>(named("httpClient"))
            val sseClientMock = declareMock<HttpClient>(named("sseClient"))

            println("http mocked $httpClientMock")
            modules(
                module {
                    single { httpClientMock }
                    single { sseClientMock }
                    single<LocationProvider> { LocationProvider() }
                    single<EnvVars> { EnvVars(backendUrl = "127.0.0.1") }
                    single<PumpApi> {
                        KtorPumpApi(
                            get(named("httpClient")),
                            get(named("sseClient")),
                            get(),
                            get()
                        )
                    }
                    single<PumpStorage> { InMemoryPumpStorage() }
                    single {
                        PumpRepository(get(), get()).apply {
                            initialize()
                        }
                    }
                },
            )
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


    private fun registerHttpResponseMocks() {
        MockProvider.register { r ->
            println("Mocked context enabled $r")
            val mockEngine = MockEngine { request ->
                if (request.url.encodedPath == "/map/station/52.4296577360724, 4.842836525941346") {
                    respondOk(
                        content = """[{"objectID":10,"title":"Shell","fuelTypes":[{"objectID":5,"title":"E5"},{"objectID":10,"title":"E10"},{"objectID":100,"title":"Diesel"}],"lat":52.429691722292816,"lon":4.843483005707954},{"objectID":12,"title":"Total","fuelTypes":[{"objectID":5,"title":"E5"},{"objectID":10,"title":"E10"},{"objectID":100,"title":"Diesel"},{"objectID":200,"title":"AdBlue"}],"lat":52.43125594769359,"lon":4.853267499617956}]""",
                    )
                } else {
                    respondBadRequest()
                }
            }
            val json = Json { ignoreUnknownKeys = true }
            HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(json, contentType = ContentType.Any)
                }
            }
        }
    }


}