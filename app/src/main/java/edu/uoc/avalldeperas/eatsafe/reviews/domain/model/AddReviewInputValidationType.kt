package edu.uoc.avalldeperas.eatsafe.reviews.domain.model

enum class AddReviewInputValidationType(val message: String?, val isValid: Boolean) {
    EmptySafety("Rate Safety between 1 and 5 points", false),
    EmptyRating("Rate Rating between 1 and 5 points", false),
    InvalidDescription("Add description between 10 and 200 words", false),
    Valid(null, true)
}