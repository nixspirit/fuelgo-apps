package com.jetbrains.kmpapp.screens.pump

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.data.pump.PumpObject
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.address_location_icon
import fuelgo_app.composeapp.generated.resources.car_icon
import fuelgo_app.composeapp.generated.resources.discount_icon
import fuelgo_app.composeapp.generated.resources.fuel_pump_icon
import fuelgo_app.composeapp.generated.resources.woman_icon
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.jetbrains.kmpapp.data.pump.HasId
import fuelgo_app.composeapp.generated.resources.gas_station_icon
import org.jetbrains.compose.resources.DrawableResource

data object PumpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PumpScreenModel = getScreenModel()

        val objects by screenModel.objects.collectAsState()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (!objectsAvailable) {
                EmptyScreenContent(Modifier.fillMaxSize())
            } else {
                ObjectTiles(
                    objects = objects,
                    onObjectClick = { objectId ->
                        navigator.push(PetrolScreen(objectId))
                    }
                )
            }
        }
    }
}

var bottomBarColor = Color(153, 102, 102);
var topBarColor = bottomBarColor;
var buttonBackgroundColor = Color(204, 204, 204)
var mainBackgroundColor = Color(229, 117, 117, 255);


@Composable
fun ObjectTiles(
    objects: List<HasId>,
    onObjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = topBarColor) {
                Row {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(8F, TextUnitType.Em),
                        color = androidx.compose.ui.graphics.Color.White,
                        text = "Select a pump"
                    )
                }
            }
        },
        bottomBar = { BottomButtons() },
        backgroundColor = mainBackgroundColor,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ) {

                LazyVerticalGrid(
                    modifier = Modifier.padding(
                        start = 30.dp,
                        end = 30.dp,
                        top = 70.dp,
                        bottom = 70.dp
                    ),
                    columns = GridCells.Adaptive(minSize = 128.dp)
                ) {
                    items(objects) {
                        PumpButton(onClick = {}, pump = it)
                    }
                }
            }
        }
    )
}


@Composable
private fun BottomButtons(
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = Modifier.height(70.dp),
        windowInsets = WindowInsets.ime,
        backgroundColor = bottomBarColor,
        content = {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MenuButton(onClick = {}, menuIcon = Res.drawable.car_icon)
                    MenuButton(onClick = {}, menuIcon = Res.drawable.address_location_icon)
                    MenuButton(onClick = {}, menuIcon = Res.drawable.discount_icon)
                    MenuButton(onClick = {}, menuIcon = Res.drawable.woman_icon)
                }
        },
    )
}

@Composable
fun MenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    menuIcon: DrawableResource
) {
    Button(
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 12.dp,
            disabledElevation = 2.dp
        ),
        onClick = onClick,
        modifier = modifier,
        content = {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Image(
                    painter = painterResource(menuIcon),
                    contentDescription = "profile"
                )
            }
        }
    )
}

@Composable
fun PumpButton(
    onClick: () -> Unit,
    pump: HasId
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 12.dp,
            disabledElevation = 2.dp
        ),
        onClick = onClick,
        modifier = Modifier.padding(5.dp),
        content = {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Image(
                    modifier = Modifier.fillMaxSize().align(alignment = Alignment.Center),
                    painter = painterResource(Res.drawable.gas_station_icon),
                    contentDescription = "pump"
                )
                Text(
                    pump.title,
                    Modifier.fillMaxSize().align(alignment = Alignment.CenterStart)
                        .wrapContentSize(),
                    color = androidx.compose.ui.graphics.Color.White,
                    fontSize = TextUnit(12F, TextUnitType.Em),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    )
}