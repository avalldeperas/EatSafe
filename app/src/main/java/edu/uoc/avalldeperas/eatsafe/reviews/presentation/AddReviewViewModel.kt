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

    private val _review = MutableStateFlow(Review())
    val review = _review.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            _review.update { loadReviewUseCase(placeId, placeName) }
            _isLoading.update { false }
        }
    }

    fun updateSafety(safety: Int) {
        _review.update { it.copy(safety = safety) }
    }

    fun updateRating(rating: Int) {
        _review.update { it.copy(rating = rating) }
    }

    fun updateDescription(description: String) {
        _review.update { it.copy(description = description) }
    }

    fun onSubmit(context: Context, backToDetail: () -> Unit) {
        _isLoading.update { true }
        val result = validateAddReviewInputUseCase(review = _review.value)

        if (!result.isValid) {
            showToast(text = result.message!!, context = context)
            _isLoading.update { false }
            return
        }

        viewModelScope.launch {
            val isSuccess = saveReviewUseCase(_review.value)
            _isLoading.update { false }
            if (!isSuccess) {
                showToast(resource = R.string.error_save_review, context = context)
                return@launch
            }
            backToDetail()
            showToast(resource = R.string.review_saved_successfully, context = context)
        }
    }
}
