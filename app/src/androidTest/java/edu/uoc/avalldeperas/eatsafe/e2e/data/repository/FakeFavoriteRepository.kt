package edu.uoc.avalldeperas.eatsafe.e2e.data.repository

import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.FIRST_PLACE_ID
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.TESTER_ID
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FakeFavoriteRepository @Inject constructor() : FavoritesRepository {

    private val favorites = mutableListOf(
        FavoritePlace("fav1", FIRST_PLACE_ID, date = Timestamp.now(), userId = TESTER_ID),
    )

    override fun getFavoritesByUser(userId: String): Flow<List<FavoritePlace>> {
        val userFavourites: List<FavoritePlace> = favorites.filter { userId == it.userId }

        return flow { emit(userFavourites) }
    }

    override fun getFavoritesByPlaceAndUser(
        placeId: String,
        userId: String
    ): Flow<List<FavoritePlace>> {

        val userPlaceFavs: List<FavoritePlace> =
            favorites.filter { fav -> userId == fav.userId && placeId == fav.placeId }

        return flow { emit(userPlaceFavs) }
    }

    override suspend fun save(favorite: FavoritePlace): Boolean {
        favorites.add(favorite)
        return true
    }

    override suspend fun delete(favorite: FavoritePlace): Boolean {
        favorites.remove(favorite)
        return true
    }
}