package edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation

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
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _places = MutableStateFlow<List<Place>>(mutableListOf())
    val places = _places.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading = _loading.asStateFlow()

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
                _places.update {
                    placeRepository.getNearbyPlaces(_currentLocation.value, radiusInKm)
                }
                _loading.value = false
            }
        }
    }

    fun getDistance(place: Place): String {
        return StringUtils.getParsedDistance(place, _user.value)
    }
}
