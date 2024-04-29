package edu.uoc.avalldeperas.eatsafe.reviews.domain.use_cases

import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import javax.inject.Inject

class LoadReviewUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(placeId: String, placeName: String): Review {
        var review = Review(placeId = placeId, placeName = placeName)

        val currentUser: User = authRepository.getCurrentUser()
        review = review.copy(userId = currentUser.uid, userName = currentUser.username)

        return review
    }
}