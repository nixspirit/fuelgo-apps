package com.jetbrains.kmpapp.screens.pump

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.data.pump.PetrolObject
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.MutableStateFlow

class PetrolScreenModel(private val pumpRepository: PumpRepository) : ScreenModel {
    fun getPetrolTypes(objectId: Int): MutableStateFlow<List<PetrolObject?>> = pumpRepository.getPetrolTypes(objectId)
}