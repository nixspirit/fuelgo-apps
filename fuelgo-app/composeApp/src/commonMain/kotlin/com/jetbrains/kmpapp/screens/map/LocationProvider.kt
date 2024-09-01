package com.jetbrains.kmpapp.screens.map

import com.jetbrains.kmpapp.expected.map.LatLong
import kotlinx.coroutines.flow.MutableStateFlow

class LocationProvider {

    fun getCurrentLocation(): MutableStateFlow<LatLong> {
        return MutableStateFlow(LatLong(52.4296577360724, 4.842836525941346))
    }
}