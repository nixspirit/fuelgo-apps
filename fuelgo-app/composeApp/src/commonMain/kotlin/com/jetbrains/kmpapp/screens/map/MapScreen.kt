package com.jetbrains.kmpapp.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.jetbrains.kmpapp.expected.map.GoogleMaps
import com.jetbrains.kmpapp.screens.MainView
import com.jetbrains.kmpapp.expected.map.LatLong;
import com.jetbrains.kmpapp.expected.map.Marker

data object MapScreen : Screen {

    @Composable
    override fun Content() {
        MainView(
            topBarText = "Select a pump",
            content = {
                val userLocations = LatLong(52.42962319302576, 4.843224920635642)
                GoogleMaps(
                    modifier = Modifier.fillMaxSize(),
                    userLocation = userLocations
                ) {
                    Marker(
                        id = "1",
                        title = "Shell",
                        position = LatLong(52.429691722292816, 4.843483005707954)
                    )
                }
            }
        )
    }
}