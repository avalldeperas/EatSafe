package edu.uoc.avalldeperas.eatsafe.explore.detail_view.state

import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review

data class DetailViewState(
    val place: Place = Place(),
    val reviews: List<Review> = mutableListOf(),
    val userFav: FavoritePlace? = null,
    val isUserReview: Boolean = false,
    val isLoading: Boolean = false,
    val userId: String = ""
)