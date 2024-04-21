package edu.uoc.avalldeperas.eatsafe.explore.list_map.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
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

    override fun getPlace(placeId: String): Flow<Place?> {
        Log.d("avb", "getPlace = get place id = $placeId")
        return try {
            ref.document(placeId).snapshots().map { it.toObject<Place>() }
        } catch (e: Exception) {
            Log.d("avb", "getPlace by id: exception = ${e.message}")
            emptyFlow()
        }
    }
}
