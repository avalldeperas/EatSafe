package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.uoc.avalldeperas.eatsafe.explore.list_map.composables.ExploreTopBar

@Composable
fun ExploreListScreen(toggleView: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {  ExploreTopBar(toggleView, Icons.Filled.Place, {}) }
        ) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Text(text = "Display list here")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreListScreenPreview() {
    ExploreListScreen({})
}