package com.jetbrains.kmpapp.data.pump

@kotlinx.serialization.Serializable
class PetrolObject(
    override val objectID: Int,
    override val title: String
) : HasId