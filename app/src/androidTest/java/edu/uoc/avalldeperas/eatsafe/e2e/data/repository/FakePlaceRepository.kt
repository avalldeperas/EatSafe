package edu.uoc.avalldeperas.eatsafe.e2e.data.repository

import com.google.android.gms.maps.model.LatLng
import edu.uoc.avalldeperas.eatsafe.e2e.data.mocks.PlacesMocks.dummyPlaces
import edu.uoc.avalldeperas.eatsafe.explore.list_map.data.PlaceRepository
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FakePlaceRepository @Inject constructor() : PlaceRepository {

    private val places = dummyPlaces()

    override fun getPlaces(): Flow<List<Place>> {
        return flow { emit(places) }
    }

    override suspend fun getNearbyPlaces(location: LatLng, radiusInKm: Double): List<Place> {
        return places
    }

    override fun getPlace(placeId: String): Flow<Place?> {
        val place: Place? = places.find { p: Place -> placeId == p.placeId }

        return flow { emit(place) }
    }
}