package com.jetbrains.kmpapp.screens.pump

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.asStateFlow

data class PumpScreen(val stationId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PumpScreenModel = getScreenModel()
        val pumps by remember { mutableStateOf(screenModel.getPumps(stationId)) }

        MainView(
            topBarText = "Select a pump",
            content = {
                GridView(
                    objects = pumps.value,
                    onObjectClick = { objectId ->
                        navigator.push(PetrolScreen(stationId, objectId))
                    },
                    Res.drawable.gas_station_icon
                )
            }
        )
    }
}
