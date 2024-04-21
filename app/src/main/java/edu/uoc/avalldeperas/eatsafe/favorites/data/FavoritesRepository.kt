package edu.uoc.avalldeperas.eatsafe.favorites.data

import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.PlaceType
import javax.inject.Inject

class FavoritesRepository @Inject constructor() {

    fun getDummyPlaces(): List<Place> {
        return (1..10).map {
            Place(
                placeId = it.toString(),
                name = "Racó del Plà",
                address = "Carrer de Llacuna 85, Barcelona",
                averageRating = 4.5,
                averageSafety = 4.2,
                type = PlaceType.Restaurant,
                distance = 100,
                image = R.drawable.restaurant_detail
            )
        }
    }
}