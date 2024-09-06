package com.jetbrains.kmpapp.screens.pump

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.data.pump.PumpRepository
import kotlinx.coroutines.flow.MutableStateFlow

class PumpScreenModel(private val pumpRepository: PumpRepository) : ScreenModel {

    fun getPumps(stationId: Int): MutableStateFlow<List<PumpObject?>> =
        pumpRepository.getPumps(stationId)

}