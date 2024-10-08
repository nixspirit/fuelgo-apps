package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.EnvVars
import com.jetbrains.kmpapp.data.pump.InMemoryPumpStorage
import com.jetbrains.kmpapp.data.pump.KtorPumpApi
import com.jetbrains.kmpapp.data.pump.PumpApi
import com.jetbrains.kmpapp.data.pump.PumpRepository
import com.jetbrains.kmpapp.data.pump.PumpStorage
import com.jetbrains.kmpapp.screens.map.LocationProvider
import com.jetbrains.kmpapp.screens.pump.PetrolScreenModel
import com.jetbrains.kmpapp.screens.pump.ProgressScreenModel
import com.jetbrains.kmpapp.screens.pump.PumpScreenModel
import com.jetbrains.kmpapp.screens.map.MapScreenModel

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.sse.SSE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<HttpClient>(named("httpClient")) {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
        }
    }
    single<HttpClient>(named("sseClient")) {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
            install(SSE) {
                showCommentEvents()
                showRetryEvents()
            }
        }
    }

    single<LocationProvider> { LocationProvider() }
    single<EnvVars> { EnvVars() }
    single<PumpApi> { KtorPumpApi(get(named("httpClient")), get(named("sseClient")), get(), get()) }
    single<PumpStorage> { InMemoryPumpStorage() }
    single<PumpRepository> { PumpRepository(get()) }
}

val screenModelsModule = module {
    factoryOf(::PumpScreenModel)
    factoryOf(::PetrolScreenModel)
    factoryOf(::ProgressScreenModel)
    factoryOf(::MapScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
