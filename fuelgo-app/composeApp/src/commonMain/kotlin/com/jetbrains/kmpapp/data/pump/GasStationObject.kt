package com.jetbrains.kmpapp.data.pump

@kotlinx.serialization.Serializable
class GasStationObject(
    override val objectID: Int,
    override val title: String,
    val lat: Double,
    val lon: Double,
    val fuelTypes: ArrayList<String>
) : HasId