package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.auth.register.data.UsersRepository
import edu.uoc.avalldeperas.eatsafe.common.util.StringUtils
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Filters
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _filters = MutableStateFlow(Filters())
    val filters = _filters.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(mutableListOf())
    val places = combine(searchText, filters, _places) { text, filters, places ->
        var placesToReturn = places
        if (filters.hasAnyValue()) {
            placesToReturn = placesToReturn.filter { it.doesMatchFilter(filters) }
        }
        if (text.isNotEmpty()) {
            placesToReturn = placesToReturn.filter { it.doesMatchSearchQuery(text) }
        }
        placesToReturn
    }.onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _places.value)

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading = _loading.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _currentLocation = MutableStateFlow(LatLng(41.40087607460614, 2.201410275782167))
    val currentLocation = _currentLocation.asStateFlow()

    private val radiusInKm: Double = 5.0

    init {
        _loading.value = true
        val currentUser = authRepository.getCurrentUser()

        viewModelScope.launch {
            usersRepository.getUser(currentUser.uid).collectLatest { user ->
                _user.update { user!! }
                _currentLocation.value = LatLng(user!!.latitude, user.longitude)
                _filters.value = _filters.value.copy(intolerances = user.intolerances)
                _places.update {
                    placeRepository.getNearbyPlaces(_currentLocation.value, radiusInKm)
                }
                _loading.value = false
            }
        }
    }

    fun getDistance(place: Place): String {
        val results = FloatArray(1)

        Location.distanceBetween(
            place.latitude,
            place.longitude,
            _user.value.latitude,
            _user.value.longitude,
            results
        )

        return StringUtils.getParsedDistance(results[0])
    }

    fun updateIntolerance(intolerance: String) {
        val intolerances = _filters.value.intolerances.toMutableList()
        if (intolerances.contains(intolerance)) {
            intolerances.remove(intolerance)
        } else {
            intolerances.add(intolerance)
        }
        _filters.update { filter -> filter.copy(intolerances = intolerances) }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}
