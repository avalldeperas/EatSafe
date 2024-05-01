package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.composables.EmptyListMessage
import edu.uoc.avalldeperas.eatsafe.common.composables.SimpleTopAppBar
import edu.uoc.avalldeperas.eatsafe.favorites.composables.FavoriteItem
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace

@Composable
fun FavoritesScreen(
    toDetailView: (String) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by favoritesViewModel.favorites.collectAsStateWithLifecycle()
    FavoritesContent(favorites = favorites, toDetailView = toDetailView, )
}

@Composable
fun FavoritesContent(
    favorites: List<FavoritePlace>,
    toDetailView: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { SimpleTopAppBar(header = R.string.favorites_header) }) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (favorites.isEmpty()) {
                    EmptyListMessage(R.string.no_favorites_yet)
                } else {
                    LazyColumn {
                        items(
                            items = favorites,
                            key = { favorite -> favorite.favoriteId }) { favorite ->
                            FavoriteItem(
                                favorite = favorite,
                                onRowClick = toDetailView
                            )
                        }
                    }
                }
            }
        }
    }
}