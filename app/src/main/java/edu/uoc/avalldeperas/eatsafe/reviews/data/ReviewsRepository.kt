package edu.uoc.avalldeperas.eatsafe.reviews.data

import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    fun getReviews(): Flow<List<Review>>
    fun getReviewsByUser(userId: String): Flow<List<Review>>
    fun getReviewsByPlace(placeId: String): Flow<List<Review>>
    suspend fun save(review: Review): Boolean
}