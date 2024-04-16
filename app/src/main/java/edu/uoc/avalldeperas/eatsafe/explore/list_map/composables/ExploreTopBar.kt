package edu.uoc.avalldeperas.eatsafe.explore.list_map.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ExploreTopBar(
    toggleView: () -> Unit,
    toggleIcon: ImageVector,
    onFilterClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .padding(12.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.weight(0.7f),
            placeholder = { Text(text = "Search...") }
        )
        IconButton(onClick = { toggleView() }, Modifier.weight(0.15f)) {
            Icon(
                imageVector = toggleIcon,
                contentDescription = "list-filter-icon"
            )

        }
        IconButton(onClick = { onFilterClick() }, modifier = Modifier.weight(0.15f)) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "map-filter-icon"
            )
        }
    }
}