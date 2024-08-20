package com.jetbrains.kmpapp.screens

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jetbrains.kmpapp.data.pump.HasId
import fuelgo_app.composeapp.generated.resources.Res
import fuelgo_app.composeapp.generated.resources.address_location_icon
import fuelgo_app.composeapp.generated.resources.back
import fuelgo_app.composeapp.generated.resources.car_icon
import fuelgo_app.composeapp.generated.resources.discount_icon
import fuelgo_app.composeapp.generated.resources.woman_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

var bottomBarColor = Color(153, 102, 102);
var topBarColor = bottomBarColor;
var buttonBackgroundColor = Color(204, 204, 204)
var mainBackgroundColor = Color(229, 117, 117, 255);

@Composable
fun MainView(
    topBarText: String = "",
    hasBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = topBarColor) {
                Row {
                    if (hasBackButton) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                stringResource(Res.string.back)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = TextUnit(6F, TextUnitType.Em),
                        color = Color.White,
                        text = topBarText
                    )
                }
            }
        },
        bottomBar = { BottomButtons() },
        backgroundColor = mainBackgroundColor,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }

        }
    )
}

@Composable
fun GridView(
    objects: List<*>,
    onObjectClick: (HasId) -> Unit,
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
                onClick = { onObjectClick(it) },
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
                horizontalArrangement = Arrangement.Center
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
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 12.dp,
            disabledElevation = 2.dp
        ),
        onClick = onClick,
        modifier = modifier.padding(horizontal = 5.dp, vertical = 5.dp),
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
        modifier = Modifier.padding(15.dp),
        content = {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(backgroundImage),
                    contentDescription = "pump"
                )
                OutlinedText(pump.title)
            }
        }
    )
}

@Composable
fun OutlinedText(text: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text,
            Modifier.fillMaxSize()
                .align(alignment = Alignment.Center)
                .wrapContentSize(),
            color = Color(153, 0, 0),
            fontSize = TextUnit(12F, TextUnitType.Em),
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle.Default.copy(
                drawStyle = Stroke(
                    width = 10f,
                    join = StrokeJoin.Round
                )
            )
        )
        Text(
            text,
            Modifier.fillMaxSize().align(alignment = Alignment.Center)
                .wrapContentSize(),
            color = Color(255, 255, 255),
            fontSize = TextUnit(12F, TextUnitType.Em),
            fontWeight = FontWeight.ExtraBold,
        )
    }
}