package com.jetbrains.kmpapp.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.jetbrains.kmpapp.expected.map.GoogleMaps
import com.jetbrains.kmpapp.screens.MainView
import com.jetbrains.kmpapp.expected.map.LatLong;

data object MapScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel: MapScreenModel = getScreenModel()
        val gasStations by screenModel.objects.collectAsState()
        val currentLocation by screenModel.currentLocation.collectAsState()

        MainView(
            topBarText = "",
            content = {
                GoogleMaps(
                    modifier = Modifier.fillMaxSize(),
                    userLocation = currentLocation,
                    gasStations = gasStations
                )
            }
        )
    }
}