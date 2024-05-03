package edu.uoc.avalldeperas.eatsafe.explore.list_map.data

import com.google.android.gms.maps.model.LatLng
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getPlaces(): Flow<List<Place>>

    suspend fun getNearbyPlaces(location: LatLng, radiusInKm: Double): List<Place>

    fun getPlace(placeId: String): Flow<Place?>
    suspend fun update(place: Place): Boolean
}