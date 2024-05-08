package edu.uoc.avalldeperas.eatsafe.auth.data

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    private val usersRef = Firebase.firestore.collection("users")

    override val user: User
        get() = TODO("Not yet implemented")

    override fun getUser(userId: String): Flow<User?> {
        return try {
            usersRef.document(userId).snapshots().map {
                it.toObject<User>()
            }
        } catch (e: Exception) {
            Log.d("avb", "getUser exception: ${e.message} ")
            emptyFlow<User>()
        }
    }

    override suspend fun save(user: User): Boolean {
        return try {
            usersRef.document(user.uid).set(user).await()
            true
        } catch (e: Exception) {
            Log.e("avb", "UsersRepository save exception: ${e.message}")
            false
        }
    }

    override suspend fun update(user: User): Boolean {
        val geoHash = getGeoHash(user.latitude, user.longitude)

        return try {
            val result = usersRef.document(user.uid).update(
                CURRENT_CITY, user.currentCity,
                LATITUDE, user.latitude,
                LONGITUDE, user.longitude,
                GEOHASH, geoHash,
                INTOLERANCES, user.intolerances
            ).await()
            Log.d("avb", "update success: $result")
            true
        } catch (e: Exception) {
            Log.e("avb", "UsersRepository update exception: ${e.message}")
            false
        }
    }

    private fun getGeoHash(lat: Double, lng: Double): String {
        return GeoFireUtils.getGeoHashForLocation(GeoLocation(lat, lng))
    }

    companion object UserFields {
        const val CURRENT_CITY = "currentCity"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val GEOHASH = "geohash"
        const val INTOLERANCES = "intolerances"
    }
}