package com.jetbrains.kmpapp.expected.map;

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState

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

    println(">>>>>>> user location $userLocation")

    GoogleMap(
        cameraPositionState = cameraPositionState,
        modifier = Modifier.background(MaterialTheme.colors.background, RectangleShape),
        uiSettings = uiSettings,
        properties = properties,
        onMapClick = {
//            infoMarkerState.hideInfoWindow()
//            infoMarker = null
        },
        googleMapOptionsFactory = {
            GoogleMapOptions().apply {
                this.backgroundColor(0x000000)
            }
        }
    ) {

        userLocation?.let { myLocation ->
            Circle(
                center = LatLng(myLocation.latitude, myLocation.longitude),
                radius = 10.0,// seenRadiusMiles.milesToMeters(),
                fillColor = Color.Blue.copy(alpha = 0.4f),
                strokeColor = Color.White.copy(alpha = 0.8f),
                strokeWidth = 4.0f
            )

        }


//        Marker(
//            state = infoMarkerState
//                .also {
//                    it.showInfoWindow() // necessary to show the infoWindow
//                },
//            alpha = 0f,
//            title = infoMarker?.title ?: "",
//            tag = infoMarker?.id ?: "",
//            snippet = infoMarker?.id ?: "",
//            icon = rememberBlankMarkerBitmap,
//            infoWindowAnchor = Offset(0.5f, .22f), // LEAVE FOR REFERENCE
//            onInfoWindowClick = {
//                onMarkerInfoClick?.run {
//                    onMarkerInfoClick(infoMarker ?: return@run)
//                }
//            },
//        )


    }
}