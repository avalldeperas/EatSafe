package edu.uoc.avalldeperas.eatsafe.auth.login.domain.model

import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import java.util.Date

data class User(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    var displayName: String? = "",
    var currentCity: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val geohash: String = "",
    val dateJoined: Timestamp = Timestamp(Date()),
    var intolerances: MutableList<String> = mutableListOf(),
    val reviews: List<Review> = listOf()
)