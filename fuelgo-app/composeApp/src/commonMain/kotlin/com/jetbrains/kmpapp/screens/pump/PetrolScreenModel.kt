package com.jetbrains.kmpapp.screens.pump

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.MuseumObject
import com.jetbrains.kmpapp.data.pump.PetrolObject
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PetrolScreenModel(private val pumpRepository: PumpRepository) : ScreenModel {
    fun getPetrolTypes(objectId: Int): MutableStateFlow<List<PetrolObject?>> = pumpRepository.getPetrolTypes(objectId)
}