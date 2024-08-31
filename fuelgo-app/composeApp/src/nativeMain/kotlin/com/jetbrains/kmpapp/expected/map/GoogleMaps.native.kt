package com.jetbrains.kmpapp.expected.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jetbrains.kmpapp.data.pump.GasStationObject

@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    userLocation: LatLong?,
    gasStations: List<GasStationObject?>
) {
}