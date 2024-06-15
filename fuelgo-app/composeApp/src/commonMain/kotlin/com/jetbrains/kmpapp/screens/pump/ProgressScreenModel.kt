package com.jetbrains.kmpapp.screens.pump

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.data.pump.PetrolObject
import com.jetbrains.kmpapp.data.pump.PetrolStatus
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.MutableStateFlow

class ProgressScreenModel(private val pumpRepository: PumpRepository) : ScreenModel {
    fun getPumpProgress(
        pumpId: Int,
        petrolId: Int
    ): MutableStateFlow<Float> = pumpRepository.getPumpStatus(pumpId, petrolId)
}