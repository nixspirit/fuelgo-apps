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
import com.jetbrains.kmpapp.data.pump.HasId
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import com.jetbrains.kmpapp.screens.GridView
import com.jetbrains.kmpapp.screens.MainView
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.fuel_pump_icon

data class PetrolScreen(val pump: HasId) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PetrolScreenModel = getScreenModel()

        val petrolTypes by screenModel.getPetrolTypes(pump.objectID).collectAsState()

        AnimatedContent(petrolTypes.isNotEmpty()) { objectsAvailable ->
            if (!objectsAvailable) {
                EmptyScreenContent(Modifier.fillMaxSize())
            } else {
                MainView(
                    topBarText = "Select fuel type",
                    hasBackButton = true,
                    onBackClick = { navigator.pop() },
                    content = {
                        GridView(
                            objects = petrolTypes,
                            onObjectClick = { petrol ->
                                navigator.push(ProgressScreen(pump, petrol))
                            },
                            Res.drawable.fuel_pump_icon
                        )
                    }
                )
            }
        }
    }
}

