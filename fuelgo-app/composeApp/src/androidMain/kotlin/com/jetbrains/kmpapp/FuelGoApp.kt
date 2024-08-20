package com.jetbrains.kmpapp

import android.app.Application
import androidx.activity.result.contract.ActivityResultContracts
import com.jetbrains.kmpapp.di.initKoin

class FuelGoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
