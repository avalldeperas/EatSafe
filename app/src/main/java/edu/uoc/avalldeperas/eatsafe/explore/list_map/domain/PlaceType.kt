package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlaceType(val displayName: String?, val imageVector: ImageVector) {
    Restaurant("Restaurant", Icons.Default.ShoppingCart)
}