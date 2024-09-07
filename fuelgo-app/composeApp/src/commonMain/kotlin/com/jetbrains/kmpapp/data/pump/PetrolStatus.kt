package com.jetbrains.kmpapp.data.pump

@kotlinx.serialization.Serializable
data class PetrolStatus(
    var id: Long = -1,
    var diff: Float = 0f,
    var total: Float = 0f,
    var event: Int = -1
)