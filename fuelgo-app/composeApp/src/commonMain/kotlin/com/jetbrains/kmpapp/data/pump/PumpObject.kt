package com.jetbrains.kmpapp.data.pump

@kotlinx.serialization.Serializable
 class PumpObject(
    override val objectID: Int,
    override val title: String
) : HasId