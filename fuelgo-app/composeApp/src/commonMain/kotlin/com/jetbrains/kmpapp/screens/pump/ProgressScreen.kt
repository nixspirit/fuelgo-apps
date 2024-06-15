package com.jetbrains.kmpapp.screens.pump

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class ProgressScreen(val pumpId: Int, val petrolId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PetrolScreenModel = getScreenModel()

        ProgressView()


    }
}


@Composable
fun ProgressView() {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = topBarColor) {
                Row {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(8F, TextUnitType.Em),
                        color = Color.White,
                        text = ""
                    )
                }
            }
        },
        bottomBar = { },
        backgroundColor = mainBackgroundColor,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ) {

            }

        }
    )
}