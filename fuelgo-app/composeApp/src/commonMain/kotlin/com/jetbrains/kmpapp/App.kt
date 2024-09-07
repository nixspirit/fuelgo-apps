package com.jetbrains.kmpapp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.kmpapp.data.pump.PetrolObject
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.screens.map.MapScreen
import com.jetbrains.kmpapp.screens.pump.PaymentScreen
import com.jetbrains.kmpapp.screens.pump.ProgressScreen
import com.jetbrains.kmpapp.screens.pump.PumpScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(MapScreen)
//        Navigator(ProgressScreen(10, PumpObject(2, "2"), PetrolObject(5, "E5")))
//        Navigator(PaymentScreen(1, PumpObject(2,"f")))
    }
}
