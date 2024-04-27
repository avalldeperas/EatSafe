package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON

@Composable
fun ExploreTopBar(
    toggleView: () -> Unit,
    toggleIcon: ImageVector,
    onFilterClick: () -> Unit,
    address: String,
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray)
                .padding(12.dp)
        ) {

            TextField(
                value = searchText,
                onValueChange = { onSearchTextChange(it) },
                modifier = Modifier.weight(0.8f),
                shape = RoundedCornerShape(80.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_hint),
                        fontSize = 12.sp
                    )
                },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                trailingIcon = {
                    IconButton(onClick = { onFilterClick() }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                    }
                },
                maxLines = 1
            )
            IconButton(onClick = { toggleView() }) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = LIST_MAP_TOGGLE_ICON
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.Place, contentDescription = "")
            Text(text = address, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ExploreTopBarPreview() {
    ExploreTopBar({}, Icons.Default.Person, {}, "", "", {})
}