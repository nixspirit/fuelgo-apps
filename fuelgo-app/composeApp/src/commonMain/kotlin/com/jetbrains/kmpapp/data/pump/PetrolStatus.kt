package com.jetbrains.kmpapp.data.pump

@kotlinx.serialization.Serializable
data class PetrolStatus(
    var id: Long,
    var diff: Float,
    var total: Float
)