package edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import javax.inject.Inject

class SaveReviewUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) {
    suspend operator fun invoke(review: Review): Boolean {
        return reviewsRepository.save(review)
    }
}