package com.jetbrains.kmpapp.screens.pump

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PumpScreenModel(pumpRepository: PumpRepository) : ScreenModel {
    val objects: StateFlow<List<PumpObject>> =
        pumpRepository.getObjects()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}