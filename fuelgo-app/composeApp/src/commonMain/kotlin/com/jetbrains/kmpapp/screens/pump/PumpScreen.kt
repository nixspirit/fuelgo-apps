package com.jetbrains.kmpapp.screens.pump

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.screens.GridView
import com.jetbrains.kmpapp.screens.MainView
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.gas_station_icon

data class PumpScreen(val stationId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PumpScreenModel = getScreenModel()
        val pumps by remember { mutableStateOf(screenModel.getPumps(stationId)) }

        MainView(
            topBarText = "Select a pump",
            hasBackButton = true,
            onBackClick = {navigator.pop()},
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
