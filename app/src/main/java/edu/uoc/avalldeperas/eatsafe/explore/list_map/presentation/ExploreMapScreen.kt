package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.GOOGLE_MAP_VIEW
import edu.uoc.avalldeperas.eatsafe.common.composables.CenteredCircularProgressIndicator
import edu.uoc.avalldeperas.eatsafe.explore.composables.AverageRatingSection
import edu.uoc.avalldeperas.eatsafe.explore.composables.ExploreTopBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.FilterBottomSheet
import edu.uoc.avalldeperas.eatsafe.explore.composables.SafetySectionWithNumber
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place

@Composable
fun ExploreMapScreen(
    toggleView: () -> Unit,
    toDetailView: (String) -> Unit,
    exploreViewModel: ExploreViewModel
) {
    val places by exploreViewModel.places.collectAsStateWithLifecycle()
    val user by exploreViewModel.user.collectAsStateWithLifecycle()
    val currentLocation by exploreViewModel.currentLocation.collectAsStateWithLifecycle()
    val isLoading by exploreViewModel.isLoading.collectAsStateWithLifecycle()
    val filters by exploreViewModel.filters.collectAsStateWithLifecycle()
    val isSearching by exploreViewModel.isSearching.collectAsStateWithLifecycle()
    val searchText by exploreViewModel.searchText.collectAsStateWithLifecycle()
    var showSheet by remember { mutableStateOf(false) }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading || isSearching) {
            CenteredCircularProgressIndicator()
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    ExploreTopBar(
                        toggleView = toggleView,
                        toggleIcon = Icons.AutoMirrored.Outlined.ViewList,
                        onFilterClick = { showSheet = true },
                        address = user.currentCity,
                        searchText = searchText,
                        onSearchTextChange = { exploreViewModel.onSearchTextChange(it) }
                    )
                },
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    GoogleMapView(
                        viewModel = exploreViewModel,
                        places = places,
                        onInfoWindowClick = toDetailView,
                        cameraPositionState = cameraPositionState,
                        uiSettings = uiSettings
                    )

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
    }
}


@Composable
fun GoogleMapView(
    viewModel: ExploreViewModel,
    places: List<Place>,
    onInfoWindowClick: (String) -> Unit,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize().testTag(GOOGLE_MAP_VIEW),
        cameraPositionState = cameraPositionState,
        properties = viewModel.state.properties,
        uiSettings = uiSettings
    ) {
        places.forEach { place ->
            MarkerInfoWindow(
                state = MarkerState(
                    position = LatLng(place.latitude, place.longitude)
                ),
                title = place.name,
                snippet = place.address,
                onInfoWindowClick = { onInfoWindowClick(place.placeId) }
            ) {
                InfoWindow(place = place)
            }
        }
    }
}

@Composable
fun InfoWindow(place: Place) {
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = place.type.imageVector, contentDescription = "")
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = place.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = place.address,
                        textAlign = TextAlign.Justify,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row {
                    SafetySectionWithNumber(
                        modifier = Modifier.weight(0.7f),
                        averageSafety = place.averageSafety,
                        fontSize = 12.sp
                    )
                    AverageRatingSection(
                        modifier = Modifier,
                        averageRating = place.averageRating,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InfoWindowPreview() {
    val testPlace = Place(
        placeId = "1",
        name = "Xorús",
        address = "Rambla del Poblenou, 105, Barcelona",
        latitude = 41.4030615965374,
        longitude = 2.1986885043302578
    )
    InfoWindow(testPlace)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreMapScreenPreview() {
    ExploreMapScreen({}, {}, hiltViewModel())
}