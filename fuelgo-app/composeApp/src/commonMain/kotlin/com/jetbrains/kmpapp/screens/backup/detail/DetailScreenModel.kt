package com.jetbrains.kmpapp.screens.backup.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.data.backup.MuseumObject
import com.jetbrains.kmpapp.data.backup.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val museumRepository: MuseumRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
