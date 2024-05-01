package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model

import com.google.firebase.firestore.Exclude
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
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
    val geohash: String = "",
    val telephone: String = "",
    val distance: Int = 100,
    val website: String = "",
    val image: Int = R.drawable.restaurant_detail,
    val totalReviews: Int = 0,
    val allergens: MutableList<Allergen> = mutableListOf(),
    val reviews: List<Review> = mutableListOf(),
    @get:Exclude
    val favorited: List<FavoritePlace> = mutableListOf()
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name$address$cuisine${type.displayName}",
            "$name $address $cuisine ${type.displayName}"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    fun doesMatchFilter(filters: Filters): Boolean {
        return allergens.map { it.displayName }.containsAll(filters.intolerances)
    }
}