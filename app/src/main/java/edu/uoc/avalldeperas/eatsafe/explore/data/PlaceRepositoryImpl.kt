package edu.uoc.avalldeperas.eatsafe.explore.data

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import edu.uoc.avalldeperas.eatsafe.explore.domain.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {

    private val ref = Firebase.firestore.collection("establishments")
    override fun getPlaces(): Flow<List<Place>> {
        return try {
            ref.snapshots().map { it.toObjects<Place>() }
        } catch (e: Exception) {
            Log.d("avb", "getPlaces: exception = ${e.message}")
            emptyFlow()
        }
    }

    override suspend fun getNearbyPlaces(location: LatLng, radiusInKm: Double): List<Place> {
        Log.d("avb", "getNearbyPlaces - location = $location, radiusInKm = $radiusInKm")
        var places: List<Place> = mutableListOf()
        val radiusInM = radiusInKm * 1000.0
        val center = GeoLocation(location.latitude, location.longitude)
        val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
        val taskList: MutableList<Task<QuerySnapshot>> = ArrayList()

        for (b in bounds) {
            val query = ref.orderBy(GEOHASH_FIELD).startAt(b.startHash).endAt(b.endHash)
            taskList.add(query.get())
        }

        val results: MutableList<Task<*>> = Tasks.whenAllComplete(taskList).await()
        for (result: Task<*> in results) {
            if (result.isSuccessful) {
                val _task: QuerySnapshot = result.await() as QuerySnapshot
                if (!_task.isEmpty) {
                    places = _task.toObjects<Place>()
                }
            }
        }

        return places
    }

    override fun getPlace(placeId: String): Flow<Place?> {
        return try {
            ref.document(placeId).snapshots().map { it.toObject<Place>() }
        } catch (e: Exception) {
            Log.d("avb", "getPlace by id: exception = ${e.message}")
            emptyFlow()
        }
    }

    override suspend fun update(place: Place): Boolean {
        Log.d("avb", "PlaceRepository:: update place: $place")
        return try {
            ref.document(place.placeId).update(
                AVERAGE_SAFETY, place.averageSafety,
                AVERAGE_RATING, place.averageRating,
                TOTAL_REVIEWS, place.totalReviews
            ).await()
            true
        } catch (e: Exception) {
            Log.e("avb", "UsersRepository update exception: ${e.message}")
            false
        }
    }

    companion object {
        const val GEOHASH_FIELD = "geohash"
        const val AVERAGE_SAFETY = "averageSafety"
        const val AVERAGE_RATING = "averageRating"
        const val TOTAL_REVIEWS = "totalReviews"
    }
}
