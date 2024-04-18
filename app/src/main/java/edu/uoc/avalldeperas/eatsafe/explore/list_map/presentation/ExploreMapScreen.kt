package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import edu.uoc.avalldeperas.eatsafe.explore.composables.ExploreTopBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.FilterBottomSheet

@Composable
fun ExploreMapScreen(
    toggleView: () -> Unit,
    toDetail: () -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ExploreTopBar(
                    toggleView = toggleView,
                    toggleIcon = Icons.AutoMirrored.Filled.List,
                    onFilterClick = { showSheet = true }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Green),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { toDetail() }) {
                    Text(text = "Racó del Pla - click me")
                }
                if (showSheet) {
                    FilterBottomSheet { showSheet = false }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreMapScreenPreview() {
    ExploreMapScreen({}, {})
}