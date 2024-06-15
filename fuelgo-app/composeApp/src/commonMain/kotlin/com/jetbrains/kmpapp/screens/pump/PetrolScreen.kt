package com.jetbrains.kmpapp.screens.pump

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.fuel_pump_icon
import fuelgo_app.composeapp.generated.resources.gas_station_icon

data class PetrolScreen(val pumpId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PetrolScreenModel = getScreenModel()

        val objects by screenModel.getPetrolTypes(pumpId).collectAsState()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (!objectsAvailable) {
                EmptyScreenContent(Modifier.fillMaxSize())
            } else {
                MainView(
                    content = {
                        GridView(
                            objects = objects,
                            onObjectClick = { petrolId ->
                                navigator.push(ProgressScreen(pumpId, petrolId))
                            },
                            Res.drawable.fuel_pump_icon
                        )
                    }
                )
            }
        }
    }
}

