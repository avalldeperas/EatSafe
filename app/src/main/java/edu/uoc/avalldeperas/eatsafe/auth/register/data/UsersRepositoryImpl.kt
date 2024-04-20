package edu.uoc.avalldeperas.eatsafe.auth.register.data

import android.util.Log
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor() : UsersRepository {

    private val db = Firebase.firestore.collection("users")

    override val user: User
        get() = TODO("Not yet implemented")

    override suspend fun getUser(userId: String): User? {
        return try {
            db.document(userId).get().await().toObject()
        } catch (e: Exception) {
            Log.d("avb", "getUser exception: ${e.message} ")
            null
        }
    }

    override suspend fun save(user: User): Boolean {
        return try {
            db.document(user.uid).set(user).await()
            true
        } catch (e: Exception) {
            Log.e("avb", "UsersRepository save exception: ${e.message}")
            false
        }
    }

    override suspend fun update(user: User): Boolean {
        val geoHash = getGeoHash(user.latitude, user.longitude)

        return try {
            val result = db.document(user.uid).update(
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