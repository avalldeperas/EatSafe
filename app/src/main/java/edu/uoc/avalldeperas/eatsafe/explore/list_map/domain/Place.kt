package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain

data class Place(
    val placeId: String,
    val name: String,
    val address: String,
    val averageRating: Double,
    val placeType: PlaceType,
    val distance: Int,
    val image: Int
)