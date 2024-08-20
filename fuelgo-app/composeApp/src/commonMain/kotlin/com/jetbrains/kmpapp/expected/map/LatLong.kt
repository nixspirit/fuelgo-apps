package com.jetbrains.kmpapp.expected.map

import kotlinx.serialization.Serializable


@Serializable
data class LatLong(val latitude: Double = 0.0, val longitude: Double = 0.0)