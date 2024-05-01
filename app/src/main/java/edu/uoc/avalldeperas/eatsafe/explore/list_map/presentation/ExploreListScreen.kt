package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.explore.composables.ExploreTopBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.FilterBottomSheet
import edu.uoc.avalldeperas.eatsafe.explore.composables.PlaceItem

@Composable
fun ExploreListScreen(
    toggleView: () -> Unit,
    toDetailView: (String) -> Unit,
    exploreViewModel: ExploreViewModel
) {
    val places by exploreViewModel.places.collectAsStateWithLifecycle()
    val user by exploreViewModel.user.collectAsStateWithLifecycle()
    val filters by exploreViewModel.filters.collectAsStateWithLifecycle()
    val searchText by exploreViewModel.searchText.collectAsStateWithLifecycle()
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
                    toggleIcon = Icons.Outlined.Map,
                    onFilterClick = { showSheet = true },
                    address = user.currentCity,
                    searchText = searchText,
                    onSearchTextChange = { exploreViewModel.onSearchTextChange(it) }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(items = places, key = { place -> place.placeId }) { place ->
                        PlaceItem(
                            place = place,
                            onRowClick = toDetailView,
                            distance = exploreViewModel.getDistance(place)
                        )
                    }
                }
            }
            if (showSheet) {
                FilterBottomSheet(
                    filters = filters,
                    onDismiss = { showSheet = false },
                    onIntoleranceClick = { exploreViewModel.updateIntolerance(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreListScreenPreview() {
    ExploreListScreen({}, {}, hiltViewModel())
}