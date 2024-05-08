package edu.uoc.avalldeperas.eatsafe.explore.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlaceType(val displayName: String?, val imageVector: ImageVector) {
    Restaurant("Restaurant", Icons.Default.Restaurant)
}