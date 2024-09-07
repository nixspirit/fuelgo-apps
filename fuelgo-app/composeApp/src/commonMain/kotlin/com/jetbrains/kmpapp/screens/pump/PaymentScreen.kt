package com.jetbrains.kmpapp.screens.pump

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Vertical
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.data.pump.HasId
import com.jetbrains.kmpapp.screens.MainView
import com.jetbrains.kmpapp.screens.map.MapScreen

class PaymentScreen(val stationId: Int, val pump: HasId) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MainView(
            topBarText = "Payment",
            content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(5f).padding(20.dp)
                            .background(Color.White)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Payment Successful",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxSize().weight(1f).padding(10.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Button(onClick = { navigator.push(MapScreen) }) {
                            Text("Done")
                        }
                    }
                }
            }
        )
    }
}