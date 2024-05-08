package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoritePlace>>(emptyList())
    var favorites: StateFlow<List<FavoritePlace>> = _favorites

    init {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            favoritesRepository.getFavoritesByUser(currentUser.uid).collectLatest { favorites ->
                _favorites.update { favorites }
            }
        }
    }
}