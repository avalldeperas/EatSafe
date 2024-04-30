package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil
import edu.uoc.avalldeperas.eatsafe.explore.detail_view.state.DetailViewState
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import edu.uoc.avalldeperas.eatsafe.navigation.Constants
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
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
    private val reviewsRepository: ReviewsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])

    private val _detailState = MutableStateFlow(DetailViewState())
    val detailState = _detailState.asStateFlow()

    init {
        _detailState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {
            _detailState.update { state -> state.copy(userId = authRepository.getCurrentUser().uid) }

            launch {
                placeRepository.getPlace(placeId).collectLatest { place ->
                    _detailState.update { currentState -> currentState.copy(place = place!!) }
                }
            }

            launch {
                reviewsRepository.getReviewsByPlace(placeId).collectLatest { reviews ->
                    _detailState.update { currentState ->
                        currentState.copy(
                            place = currentState.place.copy(
                                averageRating = calculateAvgRating(reviews),
                                averageSafety = calculateAvgSafety(reviews),
                                totalReviews = reviews.size,
                                reviews = reviews
                            ),
                            isUserReview = reviews.map { it.userId }
                                .contains(_detailState.value.userId)
                        )
                    }
                }
            }

            favoritesRepository.getFavoritesByPlaceAndUser(placeId, _detailState.value.userId)
                .collectLatest { favorites ->
                    _detailState.update { currentState ->
                        currentState.copy(
                            userFav = if (favorites.isNotEmpty()) favorites[0] else null,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun calculateAvgSafety(reviews: List<Review>): Double {
        val totalSafety = reviews.sumOf { it.safety }
        return totalSafety.toDouble() / reviews.size.toDouble()
    }

    private fun calculateAvgRating(reviews: List<Review>): Double {
        val totalSafety = reviews.sumOf { it.rating }
        return totalSafety.toDouble() / reviews.size.toDouble()
    }

    fun addFavourite(context: Context) {
        var fav: FavoritePlace
        viewModelScope.launch {
            if (_detailState.value.userFav != null) {
                fav = buildFavorite(
                    _detailState.value.userId,
                    _detailState.value.userFav!!.favoriteId
                )
                val result = favoritesRepository.delete(fav)
                if (result) {
                    ToastUtil.showToast("Place removed from favorites", context)
                } else {
                    Log.d("avb", "addFavourite: an exception occurred, check logs")
                }
            } else {
                fav = buildFavorite(_detailState.value.userId, "")
                val result = favoritesRepository.save(fav)
                if (result) {
                    ToastUtil.showToast("Place added to favorites!", context)
                } else {
                    Log.d("avb", "addFavourite: an exception occurred, check logs")
                }
            }
        }
    }

    private fun buildFavorite(uid: String, favoriteId: String): FavoritePlace {
        val place = _detailState.value.place
        return FavoritePlace(
            favoriteId = favoriteId,
            placeId = place.placeId,
            userId = uid,
            name = place.name,
            image = place.image,
            type = place.type,
            address = place.address
        )
    }
}
