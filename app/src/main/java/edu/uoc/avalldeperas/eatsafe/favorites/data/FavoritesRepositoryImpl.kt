package edu.uoc.avalldeperas.eatsafe.favorites.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor() : FavoritesRepository {

    private val favoritesRef = Firebase.firestore.collection("favorites")

    override fun getFavoritesByUser(userId: String): Flow<List<FavoritePlace>> {
        Log.d("avb", "getFavoritesByUser: userid = $userId")
        return try {
            favoritesRef.whereEqualTo(USER_ID, userId)
                .orderBy(DATE, Query.Direction.DESCENDING)
                .snapshots()
                .map { it.toObjects<FavoritePlace>() }
        } catch (e: Exception) {
            Log.d("avb", "getFavoritesByUser: exception = ${e.message}")
            emptyFlow()
        }
    }

    override fun getFavoritesByPlaceAndUser(
        placeId: String,
        userId: String,
    ): Flow<List<FavoritePlace>> {
        Log.d("avb", "getFavoritesByPlaceAndUser: placeId = $placeId, userid = $userId")
        return try {
            favoritesRef.whereEqualTo(PLACE_ID, placeId).whereEqualTo(USER_ID, userId).snapshots()
                .map { it.toObjects<FavoritePlace>() }
        } catch (e: Exception) {
            Log.d("avb", "getFavoritesByPlaceAndUser: exception = ${e.message}")
            emptyFlow()
        }
    }

    override suspend fun save(favorite: FavoritePlace): Boolean {
        return try {
            val document = favoritesRef.document()
            favorite.favoriteId = document.id
            document.set(favorite).await()
            true
        } catch (e: Exception) {
            Log.e("avb", "save favorite exception: ${e.message}")
            false
        }
    }

    override suspend fun delete(favorite: FavoritePlace): Boolean {
        Log.d("avb", "deleting favorite: $favorite")
        return try {
            favoritesRef.document(favorite.favoriteId).delete().await()
            true
        } catch (e: Exception) {
            Log.e("avb", "delete favorite exception: ${e.message}")
            false
        }
    }

    companion object {
        const val USER_ID = "userId"
        const val PLACE_ID = "placeId"
        const val DATE = "date"
    }
}
