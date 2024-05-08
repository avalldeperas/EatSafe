package edu.uoc.avalldeperas.eatsafe.favorites.domain.model

import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.explore.domain.model.PlaceType
import java.util.Date

data class FavoritePlace(
    var favoriteId: String = "",
    val placeId: String = "",
    val userId: String = "",
    val name: String = "",
    val address: String = "",
    val type: PlaceType = PlaceType.Restaurant,
    val image: Int = R.drawable.restaurant_detail,
    val date: Timestamp = Timestamp(Date())
)