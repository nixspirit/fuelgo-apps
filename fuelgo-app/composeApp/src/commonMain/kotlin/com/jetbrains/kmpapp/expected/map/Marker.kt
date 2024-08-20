package com.jetbrains.kmpapp.expected.map

import kotlinx.serialization.Serializable


@Serializable
data class Marker(
    val id: String = "",
    val position: LatLong = LatLong(0.0, 0.0),
    val title: String = "",
    val subtitle: String = ""
)