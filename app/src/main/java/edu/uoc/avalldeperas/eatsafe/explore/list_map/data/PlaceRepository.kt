package edu.uoc.avalldeperas.eatsafe.explore.list_map.data

import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getPlaces(): Flow<List<Place>>

    fun getPlace(placeId: String): Flow<Place?>
}