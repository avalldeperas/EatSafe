package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model

import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Allergen
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review

data class Place(
    val placeId: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val averageRating: Double = 0.0,
    val averageSafety: Double = 0.0,
    val type: PlaceType = PlaceType.Restaurant,
    val cuisine: String = "",
    val telephone: String = "",
    val distance: Int = 100,
    val website: String = "",
    val image: Int = R.drawable.restaurant_detail,
    val totalReviews: Int = 0,
    val allergens: MutableList<Allergen> = mutableListOf(),
    val reviews: List<Review> = mutableListOf()
)