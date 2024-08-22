package com.jetbrains.kmpapp.expected.map;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.LightingColorFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jetbrains.kmpapp.R
import com.jetbrains.kmpapp.screens.pump.PetrolScreen
import com.jetbrains.kmpapp.screens.pump.PumpScreen
import okhttp3.internal.wait


//https://github.com/realityexpander/FredsRoadtripStoryteller/blob/main/shared/src/androidMain/kotlin/GoogleMaps.kt
//https://developers.google.com/maps/documentation/android-sdk/maps-compose

// Android Google Maps implementation
@NoLiveLiterals
@OptIn(MapsComposeExperimentalApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    isMapOptionSwitchesVisible: Boolean,
    isTrackingEnabled: Boolean,
    userLocation: LatLong?,
    markers: List<Marker>?,
    shouldCalcClusterItems: Boolean,
    onDidCalculateClusterItemList: () -> Unit, // Best for setting initial camera position bc zoom level is forced
    shouldSetInitialCameraPosition: CameraPosition?, // Best for tracking user location
    shouldCenterCameraOnLatLong: LatLong?, // Best for showing a bunch of markers
    onDidCenterCameraOnLatLong: () -> Unit,
    cameraLocationBounds: CameraLocationBounds?,
    polyLine: List<LatLong>?,
    onMapClick: ((LatLong) -> Unit)?,
    onMapLongClick: ((LatLong) -> Unit)?,
    onMarkerInfoClick: ((Marker) -> Unit)?,
    seenRadiusMiles: Double,
    cachedMarkersLastUpdatedLocation: Location?,
    onToggleIsTrackingEnabledClick: (() -> Unit)?,
    onFindMeButtonClick: (() -> Unit)?,
    isMarkersLastUpdatedLocationVisible: Boolean,
    shouldShowInfoMarker: Marker?,
    onDidShowInfoMarker: () -> Unit,
    shouldZoomToLatLongZoom: LatLongZoom?,
    onDidZoomToLatLongZoom: () -> Unit,
    shouldAllowCacheReset: Boolean,
    onDidAllowCacheReset: () -> Unit,
) {

    val navigator = LocalNavigator.currentOrThrow
    val cameraPositionState = rememberCameraPositionState {
        if (userLocation != null) {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                LatLng(userLocation.latitude, userLocation.longitude),
                17f
            )
        }
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = false, //!isTrackingEnabled,
                compassEnabled = false,
                mapToolbarEnabled = false,
                zoomControlsEnabled = false,  // the +/- buttons (obscures the FAB)
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true,
                rotationGesturesEnabled = false,
            )
        )
    }
//    val mapStyle = mapStyleLight()

    var properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,  // always show the dot
                minZoomPreference = 1f,
                maxZoomPreference = 25f,
//                mapStyleOptions = MapStyleOptions(mapStyle)
            )
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        modifier = Modifier.background(MaterialTheme.colors.background, RectangleShape),
        uiSettings = uiSettings,
        properties = properties,
        onMapClick = { },
        googleMapOptionsFactory = {
            GoogleMapOptions().apply {
                this.backgroundColor(0x000000)
            }
        }
    ) {

        userLocation?.let { myLocation ->

            val loc = LatLng(myLocation.latitude, myLocation.longitude)
            IconMarker(
                context = LocalContext.current,
                position = loc,
                title = "My location",
                iconId = R.drawable.parking_point_icon,
                width = 130,
                height = 180,
                android.graphics.Color.BLUE,
                onMarkerInfoClick = { println("clicked car") }
            )
        }

        GasStationMarker(
            context = LocalContext.current,
            position = LatLng(52.429691722292816, 4.843483005707954),
            onMarkerInfoClick = { navigator.push(PumpScreen) }
        )

    }
}

//https://github.com/BoltUIX/Compose-Google-Map
@Composable
fun IconMarker(
    context: Context,
    position: LatLng,
    title: String,
    iconId: Int,
    width: Int,
    height: Int,
    color: Int,
    onMarkerInfoClick: () -> Unit
) {
    val icon = bitmapDescriptorFromVector(context, iconId, width, height, color)
    Marker(
        state = rememberMarkerState(position = position),
        title = title,
        alpha = 1.0f,
        icon = icon,
        onInfoWindowClick = { onMarkerInfoClick() }
    )
}

@Composable
fun GasStationMarker(
    context: Context,
    position: LatLng,
    onMarkerInfoClick: () -> Unit
) {
    val icon = bitmapDescriptorFromVector(
        context,
        R.drawable.fuel_station_location_icon,
        180,
        190,
        android.graphics.Color.GREEN
    )
    MarkerInfoWindow(
        state = rememberMarkerState(position = position),
        icon = icon,
        onInfoWindowClick = { onMarkerInfoClick() }
    ) { marker ->
        marker.title = "Shell"
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                )
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Shell",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.h5,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "E5, E10, Diesel",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.body1,
                    color = Color.Blue,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

//https://github.com/BoltUIX/Compose-Google-Map/blob/main/app/src/main/java/com/compose/example/MainActivity.kt

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
    width: Int,
    height: Int,
    color: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)

    val lightingColorFilter = LightingColorFilter(android.graphics.Color.CYAN, color)
    drawable.colorFilter = lightingColorFilter
    drawable.draw(canvas)

    val scaled = Bitmap.createScaledBitmap(bm, width, height, true)
    return BitmapDescriptorFactory.fromBitmap(scaled)
}