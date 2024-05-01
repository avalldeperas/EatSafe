package edu.uoc.avalldeperas.eatsafe.reviews.presentation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
import edu.uoc.avalldeperas.eatsafe.navigation.Constants
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.LoadReviewUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.SaveReviewUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases.ValidateAddReviewInputUseCase
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.state.AddReviewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val validateAddReviewInputUseCase: ValidateAddReviewInputUseCase,
    private val loadReviewUseCase: LoadReviewUseCase,
    private val saveReviewUseCase: SaveReviewUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])
    private val placeName: String = checkNotNull(savedStateHandle[Constants.PLACE_NAME_PARAM])

    private val _addReviewState = MutableStateFlow(AddReviewState())
    val addReviewState = _addReviewState.asStateFlow()

    init {
        _addReviewState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {

            _addReviewState.update { state ->
                state.copy(review = loadReviewUseCase(placeId, placeName))
            }

            _addReviewState.update { state -> state.copy(isLoading = false) }

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

        viewModelScope.launch {
            val isSuccess = saveReviewUseCase(newReview)
            _addReviewState.update { state -> state.copy(isLoading = false) }
            if (!isSuccess) {
                showToast(resource = R.string.error_save_review, context = context)
                return@launch
            }
            backToDetail()
            showToast(resource = R.string.review_saved_successfully, context = context)
        }
    }

    private fun buildReview(): Review {
        return _addReviewState.value.review.copy(
            rating = _addReviewState.value.rating,
            safety = _addReviewState.value.safety,
            description = _addReviewState.value.description
        )
    }
}