package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain

import edu.uoc.avalldeperas.eatsafe.R

data class Place(
    val placeId: String,
    val name: String,
    val address: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val averageRating: Double = 4.6,
    val averageSafety: Double = 3.5,
    val placeType: PlaceType = PlaceType.Restaurant,
    val distance: Int = 100,
    val image: Int = R.drawable.restaurant_detail,
)