package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
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
    private val reviewsRepository: ReviewsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])

    private val _place = MutableStateFlow(Place())
    val place = _place.asStateFlow()

    private lateinit var userId: String

    private val _userFav: MutableStateFlow<FavoritePlace?> = MutableStateFlow(null)
    val userFav = _userFav.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            userId = authRepository.getCurrentUser().uid

            launch {
                placeRepository.getPlace(placeId).collectLatest { place ->
                    _place.update { place!! }
                }
            }

            launch {
                reviewsRepository.getReviewsByPlace(placeId).collectLatest { reviews ->
                    _place.value = _place.value.copy(reviews = reviews)
                }
            }

            favoritesRepository.getFavoritesByPlaceAndUser(placeId, userId)
                .collectLatest { favorites ->
                    _userFav.value = if (favorites.isNotEmpty()) favorites[0] else null
                    _isLoading.update { false }
                }
        }
    }

    fun addFavourite(context: Context) {
        var fav: FavoritePlace
        viewModelScope.launch {
            if (_userFav.value != null) {
                fav = buildFavorite(userId, _userFav.value!!.favoriteId)
                val result = favoritesRepository.delete(fav)
                if (result) {
                    ToastUtil.showToast("Place removed from favorites", context)
                } else {
                    Log.d("avb", "addFavourite: an exception occurred pls check logs")
                }
            } else {
                fav = buildFavorite(userId, "")
                val result = favoritesRepository.save(fav)
                if (result) {
                    ToastUtil.showToast("Place added to favorites!", context)
                } else {
                    Log.d("avb", "addFavourite: an exception occurred check logs")
                }
            }
        }
    }

    private fun buildFavorite(uid: String, favoriteId: String): FavoritePlace {
        return FavoritePlace(
            favoriteId = favoriteId,
            placeId = _place.value.placeId,
            userId = uid,
            name = _place.value.name,
            image = _place.value.image,
            type = _place.value.type,
            address = _place.value.address
        )
    }
}
