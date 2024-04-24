package edu.uoc.avalldeperas.eatsafe.favorites.data

import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoritesByUser(userId: String): Flow<List<FavoritePlace>>
    fun getFavoritesByPlaceAndUser(placeId: String, userId: String): Flow<List<FavoritePlace>>
    suspend fun save(favorite: FavoritePlace): Boolean
    suspend fun delete(favorite: FavoritePlace): Boolean
}