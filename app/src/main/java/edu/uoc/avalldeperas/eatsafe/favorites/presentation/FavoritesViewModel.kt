package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.favorites.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
): ViewModel() {

    private val _favorites = MutableStateFlow<List<Place>>(emptyList())
    var favorites: StateFlow<List<Place>> = _favorites
    init {
        viewModelScope.launch {
            _favorites.value = favoritesRepository.getDummyPlaces()
        }
    }
}