package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(isMyLocationEnabled = false)
)