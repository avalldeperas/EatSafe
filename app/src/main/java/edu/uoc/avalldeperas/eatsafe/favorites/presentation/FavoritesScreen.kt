package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.composables.SimpleTopAppBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.PlaceItem
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.getDummyPlaces

@Composable
fun FavoritesScreen(toDetailView: () -> Unit) {
    val places = getDummyPlaces()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { SimpleTopAppBar(header = R.string.favorites_header) }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(items = places, key = { place -> place.placeId }) { place ->
                        PlaceItem(place = place, onRowClick = toDetailView, isFavorites = true)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen({})
}