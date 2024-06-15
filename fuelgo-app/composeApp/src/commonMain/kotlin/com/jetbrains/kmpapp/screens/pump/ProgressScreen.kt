package com.jetbrains.kmpapp.screens.pump

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.data.pump.HasId
import com.jetbrains.kmpapp.screens.utils.AnimatedCounter

data class ProgressScreen(val pump: HasId, val petrol: HasId) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ProgressScreenModel = getScreenModel()
        val litreCounter =
            screenModel.getPumpProgress(pump.objectID, petrol.objectID).collectAsState()

        ProgressView(petrol = petrol, progressObject = litreCounter)
    }
}


@Composable
fun ProgressView(
    petrol: HasId,
    progressObject: State<Float>
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = topBarColor) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(8F, TextUnitType.Em),
                        color = Color.White,
                        text = ""
                    )
//                    LinearProgressIndicator(
//                        modifier = Modifier.fillMaxWidth().width(64.dp),
//                        color = Color.DarkGray,
//                    )
                }
            }
        },
        bottomBar = { },
        backgroundColor = mainBackgroundColor,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                            .padding(start = 50.dp, end = 50.dp, top = 200.dp, bottom = 300.dp),
                        color = Color.DarkGray,
                    )
                    Text(
                        petrol.title,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TextUnit(14F, TextUnitType.Em),
                        modifier = Modifier.fillMaxSize().padding(50.dp)
                    )
                    AnimatedCounter(
                        count = progressObject.value,
                        style = MaterialTheme.typography.h1
                    )
                }
            }
        }
    )
}
