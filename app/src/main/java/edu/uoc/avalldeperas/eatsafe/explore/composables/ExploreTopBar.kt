package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FILTER_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON

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
            placeholder = { Text(text = stringResource(R.string.search_hint)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }
        )
        IconButton(onClick = { toggleView() }, Modifier.weight(0.15f)) {
            Icon(
                imageVector = toggleIcon,
                contentDescription = LIST_MAP_TOGGLE_ICON
            )

        }
        IconButton(onClick = { onFilterClick() }, modifier = Modifier.weight(0.15f)) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = FILTER_ICON
            )
        }
    }
}