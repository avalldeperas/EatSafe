package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.navigation.Constants
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository,
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])

    private val _place = MutableStateFlow(Place())
    val place = _place.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            placeRepository.getPlace(placeId).collectLatest { place ->
                _place.update { place!! }

                reviewsRepository.getReviewsByPlace(placeId).collectLatest { reviews ->
                    _place.value = _place.value.copy(reviews = reviews)
                    _isLoading.update { false }
                }
            }
        }
    }
}