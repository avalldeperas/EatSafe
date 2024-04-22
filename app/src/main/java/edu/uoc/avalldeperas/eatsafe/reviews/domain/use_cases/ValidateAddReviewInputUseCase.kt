package edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.AddReviewInputValidationType
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import javax.inject.Inject

class ValidateAddReviewInputUseCase @Inject constructor(

) {
    operator fun invoke(review: Review): AddReviewInputValidationType {
        if (review.safety == 0) {
            return AddReviewInputValidationType.EmptySafety
        }

        if (review.rating == 0) {
            return AddReviewInputValidationType.EmptyRating
        }

        if (review.description.length < MIN_DESCRIPTION_LENGTH
            || review.description.length > MAX_DESCRIPTION_LENGTH
        ) {
            return AddReviewInputValidationType.InvalidDescription
        }

        return AddReviewInputValidationType.Valid
    }

    companion object {
        const val MIN_DESCRIPTION_LENGTH = 10
        const val MAX_DESCRIPTION_LENGTH = 200
    }
}