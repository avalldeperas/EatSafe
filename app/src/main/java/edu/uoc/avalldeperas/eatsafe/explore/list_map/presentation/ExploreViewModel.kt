package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {
    var state by mutableStateOf(MapState())

    private val _places = MutableStateFlow<List<Place>>(mutableListOf())
    val places = _places.asStateFlow()

    val currentLocation = LatLng(41.40087607460614, 2.201410275782167)

    init {
        viewModelScope.launch {
            placeRepository.getPlaces().collectLatest { places -> _places.update { places } }
        }
    }
}
