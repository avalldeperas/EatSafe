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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import edu.uoc.avalldeperas.eatsafe.explore.composables.AverageRatingSection
import edu.uoc.avalldeperas.eatsafe.explore.composables.ExploreTopBar
import edu.uoc.avalldeperas.eatsafe.explore.composables.FilterBottomSheet
import edu.uoc.avalldeperas.eatsafe.explore.composables.SafetySectionWithNumber
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.Place

@Composable
fun ExploreMapScreen(
    toggleView: () -> Unit,
    toDetailView: (String) -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    var showSheet by remember { mutableStateOf(false) }
    val user = LatLng(41.40087607460614, 2.201410275782167)
    val places = getMapPlaces()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(user, 15f)
    }
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

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
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GoogleMapView(
                    cameraPositionState = cameraPositionState,
                    viewModel = viewModel,
                    uiSettings = uiSettings,
                    places = places,
                    onInfoWindowClick = toDetailView
                )
                if (showSheet) {
                    FilterBottomSheet { showSheet = false }
                }
            }
        }
    }
}

@Composable
fun GoogleMapView(
    cameraPositionState: CameraPositionState,
    viewModel: MapViewModel,
    uiSettings: MapUiSettings,
    places: List<Place>,
    onInfoWindowClick: (String) -> Unit
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
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
        modifier = Modifier.background(color = Color.White).clip(RoundedCornerShape(16.dp))
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
                    Icon(imageVector = place.placeType.imageVector, contentDescription = "")
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = place.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Place, contentDescription = "")
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = place.address,
                        textAlign = TextAlign.Justify,
                        fontSize = 10.sp
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
                        averageRating = place.averageSafety,
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
    InfoWindow(getMapPlaces()[2])
}

fun getMapPlaces(): List<Place> {
    return listOf(
        Place(
            placeId = "1",
            name = "Xorús",
            address = "Rambla del Poblenou, 105, Barcelona",
            latitude = 41.4030615965374,
            longitude = 2.1986885043302578
        ),
        Place(
            placeId = "2",
            name = "Can Recasens",
            address = "Rambla del Poblenou, 102, Barcelona",
            latitude = 41.40269141325675, longitude = 2.199825760890306
        ),
        Place(
            placeId = "3",
            name = "Restaurant Puerto Escondido",
            address = "Carrer de Marià Aguiló, 26, Sant Martí, 08005 Barcelona",
            latitude = 41.40352834633225, longitude = 2.1997828455445676
        ),
        Place(
            placeId = "4",
            name = "Can Culleres",
            address = "C/ de Bilbao, 79, Sant Martí, 08005 Barcelona",
            latitude = 41.403866335474774, longitude = 2.2022504779200354
        ),
        Place(
            placeId = "5",
            name = "Sala Beckett",
            address = "Carrer de Bilbao, 79, Sant Martí, 08005 Barcelona",
            latitude = 41.40352834633225, longitude = 2.1997828455445676
        ),
        Place(
            placeId = "6",
            name = "ApriBocca - Italian Restaurant",
            address = "Plaça de Sant Bernat Calbó, 6, Sant Martí, 08005 Barcelona",
            41.40010007133809, 2.2050399753930434
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreMapScreenPreview() {
    ExploreMapScreen({}, {})
}