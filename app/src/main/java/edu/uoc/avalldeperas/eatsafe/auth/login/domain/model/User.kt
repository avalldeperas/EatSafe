package edu.uoc.avalldeperas.eatsafe.auth.login.domain.model

import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import java.util.Date

data class User(
    val uid: String = "",
    val email: String = "",
    var currentCity: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val geohash: String = "",
    val dateJoined: Timestamp = Timestamp(Date()),
    val intolerances: List<Intolerance> = listOf()
)