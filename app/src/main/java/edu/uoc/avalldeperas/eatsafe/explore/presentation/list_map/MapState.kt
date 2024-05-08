package edu.uoc.avalldeperas.eatsafe.explore.presentation.list_map

import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(isMyLocationEnabled = false)
)