package edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import javax.inject.Inject

class SaveReviewUseCase @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val placeRepository: PlaceRepository,
) {
    suspend operator fun invoke(review: Review, place: Place): Boolean {
        if (reviewsRepository.save(review)) {
            return placeRepository.update(place)
        }

        return false
    }
}