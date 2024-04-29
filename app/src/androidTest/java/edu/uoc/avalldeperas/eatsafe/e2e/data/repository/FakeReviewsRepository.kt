package edu.uoc.avalldeperas.eatsafe.e2e.data.repository

import android.util.Log
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.FIRST_PLACE_ID
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_ID
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_USERNAME
import edu.uoc.avalldeperas.eatsafe.reviews.data.ReviewsRepository
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FakeReviewsRepository @Inject constructor() : ReviewsRepository {

    private val reviews = mutableListOf(
        Review("review1", TESTER_ID, TESTER_USERNAME, FIRST_PLACE_ID)
    )

    override fun getReviews(): Flow<List<Review>> {
        return flow { emit(reviews) }
    }

    override fun getReviewsByUser(userId: String): Flow<List<Review>> {
        val userReviews = reviews.filter { review -> userId == review.userId }
        return flow { emit(userReviews) }
    }

    override fun getReviewsByPlace(placeId: String): Flow<List<Review>> {
        val placeReviews = reviews.filter { review -> placeId == review.placeId }
        Log.d("avb", "save: reviews = $placeReviews")
        return flow { emit(placeReviews) }
    }

    override suspend fun save(review: Review): Boolean {
        reviews.add(review)
        Log.d("avb", "save: reviews = $reviews")
        return true
    }
}