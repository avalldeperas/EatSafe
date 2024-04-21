package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model

import edu.uoc.avalldeperas.eatsafe.R

data class Place(
    val placeId: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val averageRating: Double = 4.6,
    val averageSafety: Double = 3.5,
    val type: PlaceType = PlaceType.Restaurant,
    val cuisine: String = "",
    val telephone: String = "",
    val distance: Int = 100,
    val website: String = "",
    val image: Int = R.drawable.restaurant_detail,
    val totalRatings: Int = 0
)