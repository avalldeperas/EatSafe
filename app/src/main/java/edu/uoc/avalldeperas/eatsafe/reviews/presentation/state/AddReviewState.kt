package edu.uoc.avalldeperas.eatsafe.reviews.presentation.state

import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review

data class AddReviewState(
    val review: Review = Review(),
    val isLoading: Boolean = false,
    val safety: Int = 0,
    val rating: Int = 0,
    val description: String = ""
)
