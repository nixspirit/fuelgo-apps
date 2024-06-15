package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.backup.InMemoryMuseumStorage
import com.jetbrains.kmpapp.data.backup.KtorMuseumApi
import com.jetbrains.kmpapp.data.backup.MuseumApi
import com.jetbrains.kmpapp.data.backup.MuseumRepository
import com.jetbrains.kmpapp.data.backup.MuseumStorage
import com.jetbrains.kmpapp.data.pump.InMemoryPumpStorage
import com.jetbrains.kmpapp.data.pump.KtorPumpApi
import com.jetbrains.kmpapp.data.pump.PumpApi
import com.jetbrains.kmpapp.data.pump.PumpRepository
import com.jetbrains.kmpapp.data.pump.PumpStorage
import com.jetbrains.kmpapp.screens.backup.detail.DetailScreenModel
import com.jetbrains.kmpapp.screens.pump.ProgressScreenModel
import com.jetbrains.kmpapp.screens.pump.PumpScreenModel
import com.jetbrains.kmpapp.screens.pump.PetrolScreenModel
import com.jetbrains.kmpapp.screens.backup.detail.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import io.ktor.client.plugins.sse.*

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
            install(SSE) {
                showCommentEvents()
                showRetryEvents()
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
