package com.jetbrains.kmpapp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.kmpapp.screens.pump.ProgressScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(ProgressScreen(1,2))
//        Navigator(PumpScreen)
    }
}
