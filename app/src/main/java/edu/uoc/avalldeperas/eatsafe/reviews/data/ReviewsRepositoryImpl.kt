package edu.uoc.avalldeperas.eatsafe.reviews.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(

) : ReviewsRepository {

    private val reviewsRef = Firebase.firestore.collection("reviews")
    override fun getReviews(): Flow<List<Review>> {
        return try {
            reviewsRef.snapshots().map { it.toObjects<Review>() }
        } catch (e: Exception) {
            Log.d("avb", "getReviews: exception = ${e.message}")
            emptyFlow()
        }
    }

    override fun getReviewsByUser(userId: String): Flow<List<Review>> {
        return try {
            reviewsRef.whereEqualTo(USER_ID, userId).snapshots().map { it.toObjects<Review>() }
        } catch (e: Exception) {
            Log.d("avb", "getReviewsByUser: exception = ${e.message}")
            emptyFlow()
        }
    }

    override fun getReviewsByPlace(placeId: String): Flow<List<Review>> {
        return try {
            reviewsRef.whereEqualTo(PLACE_ID, placeId).orderBy(DATE, Query.Direction.DESCENDING)
                .snapshots().map { it.toObjects<Review>() }
        } catch (e: Exception) {
            Log.d("avb", "getReviewsByPlace: exception = ${e.message}")
            emptyFlow()
        }
    }

    override suspend fun save(review: Review): Boolean {
        return try {
            reviewsRef.add(review).await()
            true
        } catch (e: Exception) {
            Log.e("avb", "save review exception: ${e.message}")
            false
        }
    }

    companion object {
        const val USER_ID = "userId"
        const val PLACE_ID = "placeId"
        const val DATE = "date"
    }
}