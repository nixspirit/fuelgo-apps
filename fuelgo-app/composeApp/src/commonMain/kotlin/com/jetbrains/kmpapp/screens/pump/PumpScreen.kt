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
import com.jetbrains.kmpapp.screens.GridView
import com.jetbrains.kmpapp.screens.MainView
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.gas_station_icon

data object PumpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PumpScreenModel = getScreenModel()
        val objects by screenModel.objects.collectAsState()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (!objectsAvailable) {
                EmptyScreenContent(Modifier.fillMaxSize())

            } else {
                MainView(
                    topBarText = "Select a pump",
                    content = {
                        GridView(
                            objects = objects,
                            onObjectClick = { objectId ->
                                navigator.push(PetrolScreen(objectId))
                            },
                            Res.drawable.gas_station_icon
                        )
                    }
                )
            }
        }
    }
}
