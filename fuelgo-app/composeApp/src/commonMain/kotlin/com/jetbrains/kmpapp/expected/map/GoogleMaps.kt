package com.jetbrains.kmpapp.expected.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//https://github.com/realityexpander/FredsRoadtripStoryteller/blob/main/shared/src/commonMain/kotlin/presentation/maps/LatLong.kt

data class LatLongZoom(
    val latLong: LatLong,
    val zoom: Float
)

@Composable
expect fun GoogleMaps(
    modifier: Modifier,
    isMapOptionSwitchesVisible: Boolean = true,
    isTrackingEnabled: Boolean = false,
    userLocation: LatLong? = null,
    markers: List<Marker>? = null,
    shouldCalcClusterItems: Boolean = false,
    onDidCalculateClusterItemList: () -> Unit = {},
    shouldSetInitialCameraPosition: CameraPosition? = null,
    shouldCenterCameraOnLatLong: LatLong? = null,
    onDidCenterCameraOnLatLong: () -> Unit = {},
    cameraLocationBounds: CameraLocationBounds? = null,
    polyLine: List<LatLong>? = null,
    onMapClick: ((LatLong) -> Unit)? = {},
    onMapLongClick: ((LatLong) -> Unit)? = {},
    onMarkerInfoClick: ((Marker) -> Unit)? = {},
    seenRadiusMiles: Double = .5,
    cachedMarkersLastUpdatedLocation: Location? = null,
    onToggleIsTrackingEnabledClick: (() -> Unit)? = null,
    onFindMeButtonClick: (() -> Unit)? = null,
    isMarkersLastUpdatedLocationVisible: Boolean = false,
    shouldShowInfoMarker: Marker? = null,
    onDidShowInfoMarker: () -> Unit = {},
    shouldZoomToLatLongZoom: LatLongZoom? = null,
    onDidZoomToLatLongZoom: () -> Unit = {},
    shouldAllowCacheReset: Boolean = true,
    onDidAllowCacheReset: () -> Unit = {},
)

