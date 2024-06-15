package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.InMemoryMuseumStorage
import com.jetbrains.kmpapp.data.KtorMuseumApi
import com.jetbrains.kmpapp.data.MuseumApi
import com.jetbrains.kmpapp.data.MuseumRepository
import com.jetbrains.kmpapp.data.MuseumStorage
import com.jetbrains.kmpapp.data.pump.InMemoryPumpStorage
import com.jetbrains.kmpapp.data.pump.KtorPumpApi
import com.jetbrains.kmpapp.data.pump.PumpApi
import com.jetbrains.kmpapp.data.pump.PumpRepository
import com.jetbrains.kmpapp.data.pump.PumpStorage
import com.jetbrains.kmpapp.screens.detail.DetailScreenModel
import com.jetbrains.kmpapp.screens.pump.ProgressScreenModel
import com.jetbrains.kmpapp.screens.pump.PumpScreenModel
import com.jetbrains.kmpapp.screens.pump.PetrolScreenModel
import com.jetbrains.kmpapp.screens.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }

    single<PumpApi> { KtorPumpApi(get()) }
    single<PumpStorage> { InMemoryPumpStorage() }
    single {
        PumpRepository(get(), get()).apply {
            initialize()
        }
    }
}

val screenModelsModule = module {
    factoryOf(::ListScreenModel)
    factoryOf(::DetailScreenModel)
    factoryOf(::PumpScreenModel)
    factoryOf(::PetrolScreenModel)
    factoryOf(::ProgressScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
