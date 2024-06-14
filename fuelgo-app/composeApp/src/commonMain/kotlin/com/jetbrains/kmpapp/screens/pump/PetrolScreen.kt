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
import fuelgo_app.composeapp.generated.resources.gas_station_icon
import org.jetbrains.compose.resources.DrawableResource

data class PetrolScreen(val objectId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: PetrolScreenModel = getScreenModel()

        val objects by screenModel.getPetrolTypes(objectId).collectAsState()

        AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
            if (!objectsAvailable) {
                EmptyScreenContent(Modifier.fillMaxSize())
            } else {
                ObjectTiles(
                    objects = emptyList(),
                    onObjectClick = { objectId ->
                        navigator.push(DetailScreen(objectId))
                    }
                )
            }
        }
    }
}

