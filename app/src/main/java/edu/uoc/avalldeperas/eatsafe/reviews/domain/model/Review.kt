package edu.uoc.avalldeperas.eatsafe.reviews.domain.model

import com.google.firebase.Timestamp
import java.util.Date

data class Review(
    val reviewId: String = "",
    val userId: String = "",
    val placeId: String = "",
    val safety: Int = 0,
    val rating: Int = 0,
    val description: String = "",
    val placeName: String = "",
    val date: Timestamp = Timestamp(Date())
)
