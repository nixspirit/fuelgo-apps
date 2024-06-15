package com.jetbrains.kmpapp.screens.pump

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class ProgressScreen(val pumpId: Int, val petrolId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ProgressScreenModel = getScreenModel()

        val litreCounter = screenModel.getPumpProgress(pumpId, petrolId).collectAsState()
        ProgressView(progressObject = litreCounter)


    }
}


@Composable
fun ProgressView(progressObject: State<Float>) {
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
                        "E5",
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

//https://github.com/philipplackner/AnimatedCounterCompose/blob/3ba8c99291de44ee77265a357335af59f4999d94/app/src/main/java/com/plcoding/animatedcountercompose/AnimatedCounter.kt#L14
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Float,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val counterString = count.toString()
        val oldCounterString = oldCount.toString()

        val countString: String = counterString.substring(0, counterString.indexOf('.') + 2)
        val oldCountString = oldCounterString.substring(0, counterString.indexOf('.') + 2)
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}
