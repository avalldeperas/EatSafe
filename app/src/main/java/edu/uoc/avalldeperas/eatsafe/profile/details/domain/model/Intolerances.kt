package edu.uoc.avalldeperas.eatsafe.profile.details.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class Intolerance(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Info,
    var enabled: Boolean = false,
) {
    fun intolerances(): List<Intolerance> {
        return listOf(
            Intolerance(
                label = "Gluten",
                icon = Icons.Filled.Search,
                enabled = false
            ),
            Intolerance(
                label = "Lactose",
                icon = Icons.Filled.Favorite,
                enabled = false
            )
        )
    }
}
