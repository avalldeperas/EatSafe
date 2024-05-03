package edu.uoc.avalldeperas.eatsafe.reviews.presentation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.navigation.Constants
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.GetUserInfoUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.SaveReviewUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.ValidateAddReviewInputUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.state.AddReviewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val validateAddReviewInputUseCase: ValidateAddReviewInputUseCase,
    private val getUserInfoUse: GetUserInfoUseCase,
    private val placeRepository: PlaceRepository,
    private val saveReviewUseCase: SaveReviewUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])
    private val placeName: String = checkNotNull(savedStateHandle[Constants.PLACE_NAME_PARAM])

    private val _addReviewState = MutableStateFlow(AddReviewState())
    val addReviewState = _addReviewState.asStateFlow()

    init {
        _addReviewState.update { state -> state.copy(isLoading = true) }

        val review = Review(placeId = placeId, placeName = placeName)

        viewModelScope.launch {

            launch {
                getUserInfoUse().collectLatest { user ->
                    _addReviewState.update { state ->
                        state.copy(
                            review = review.copy(userName = user!!.username, userId = user.uid)
                        )
                    }
                    _addReviewState.update { state -> state.copy(isLoading = false) }
                }
            }
            placeRepository.getPlace(placeId).collect {
                _addReviewState.update { state -> state.copy(place = it!!) }
            }
        }
    }

    fun updateSafety(safety: Int) {
        _addReviewState.update { state -> state.copy(safety = safety) }
    }

    fun updateRating(rating: Int) {
        _addReviewState.update { state -> state.copy(rating = rating) }
    }

    fun updateDescription(description: String) {
        _addReviewState.update { state -> state.copy(description = description) }
    }

    fun onSubmit(context: Context, backToDetail: () -> Unit) {
        _addReviewState.update { state -> state.copy(isLoading = true) }

        val newReview = buildReview()
        val result = validateAddReviewInputUseCase(review = buildReview())

        if (!result.isValid) {
            showToast(text = result.message!!, context = context)
            _addReviewState.update { state -> state.copy(isLoading = false) }
            return
        }

        val updatedPlace = buildPlace()
        viewModelScope.launch {
            val isSuccess = saveReviewUseCase(newReview, updatedPlace)
            if (!isSuccess) {
                showToast(resource = R.string.error_save_review, context = context)
                return@launch
            }

            _addReviewState.update { state -> state.copy(isLoading = false) }
            showToast(resource = R.string.review_saved_successfully, context = context)
            backToDetail()
        }
    }

    private fun buildPlace(): Place {
        val place = _addReviewState.value.place
        return place.copy(
            averageSafety = place.calculateNewAverageSafety(_addReviewState.value.safety),
            averageRating = place.calculateNewAverageRating(_addReviewState.value.rating),
            totalReviews = place.totalReviews + 1
        )
    }

    private fun buildReview(): Review {
        return _addReviewState.value.review.copy(
            rating = _addReviewState.value.rating,
            safety = _addReviewState.value.safety,
            description = _addReviewState.value.description
        )
    }
}