package com.jetbrains.kmpapp.screens.pump

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.address_location_icon
import fuelgo_app.composeapp.generated.resources.car_icon
import fuelgo_app.composeapp.generated.resources.discount_icon
import fuelgo_app.composeapp.generated.resources.woman_icon
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.UiComposable
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
                MainView(
                    content = {
                        GridView(
                            objects = objects,
                            onObjectClick = { objectId ->
                                navigator.push(PetrolScreen(objectId))
                            },
                            Res.drawable.gas_station_icon
                        )
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
fun MainView(
    content: @Composable () -> Unit,
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
                        color = Color.White,
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
                content()
            }

        }
    )
}

@Composable
fun GridView(
    objects: List<*>,
    onObjectClick: (Int) -> Unit,
    itemBackgroundImage: DrawableResource,
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
            it as HasId
            PumpButton(
                onClick = { onObjectClick(it.objectID) },
                pump = it,
                itemBackgroundImage
            )
        }
    }
}


@Composable
fun BottomButtons(
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
    pump: HasId,
    backgroundImage: DrawableResource
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
                    painter = painterResource(backgroundImage),
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