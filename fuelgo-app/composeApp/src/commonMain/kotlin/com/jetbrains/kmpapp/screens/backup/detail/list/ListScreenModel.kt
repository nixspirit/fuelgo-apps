package com.jetbrains.kmpapp.screens.backup.detail.list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.backup.MuseumObject
import com.jetbrains.kmpapp.data.backup.MuseumRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListScreenModel(museumRepository: MuseumRepository) : ScreenModel {
    val objects: StateFlow<List<MuseumObject>> =
        museumRepository.getObjects()
            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
