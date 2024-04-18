package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.explore.composables.ExploreTopBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.FilterBottomSheet
import edu.uoc.avalldeperas.eatsafe.explore.composables.PlaceItem
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.Place
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.PlaceType

@Composable
fun ExploreListScreen(toggleView: () -> Unit, toDetailView: () -> Unit) {
    val places = getDummyPlaces()
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
                    toggleIcon = Icons.Filled.Place,
                    onFilterClick = { showSheet = true })
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(items = places, key = { place -> place.placeId }) { place ->
                        PlaceItem(place = place, onRowClick = toDetailView)
                    }
                }
            }
            if (showSheet) {
                FilterBottomSheet { showSheet = false }
            }
        }
    }
}

fun getDummyPlaces(): List<Place> {
    return (1..10).map {
        Place(
            placeId = it.toString(),
            name = "Racó del Plà",
            address = "Carrer de Llacuna 85, Barcelona",
            averageRating = 4.5,
            placeType = PlaceType.Restaurant,
            distance = 100,
            image = R.drawable.restaurant_detail
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreListScreenPreview() {
    ExploreListScreen({}, {})
}