package com.jetbrains.kmpapp.screens.map

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.pump.GasStationObject
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MapScreenModel(pumpRepository: PumpRepository) : ScreenModel {

    val objects: StateFlow<List<GasStationObject?>> = pumpRepository.getGasStations()
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

}