package edu.uoc.avalldeperas.eatsafe.reviews.presentation

import edu.uoc.avalldeperas.eatsafe.explore.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review

data class AddReviewState(
    val review: Review = Review(),
    val isLoading: Boolean = false,
    val safety: Int = 0,
    val rating: Int = 0,
    val place: Place = Place(),
    val description: String = "",
)
