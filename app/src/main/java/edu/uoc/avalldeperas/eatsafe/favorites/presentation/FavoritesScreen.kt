package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.composables.EmptyListMessageIcon
import edu.uoc.avalldeperas.eatsafe.common.composables.SimpleTopAppBar
import edu.uoc.avalldeperas.eatsafe.favorites.composables.FavoriteItem
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace

@Composable
fun FavoritesScreen(
    toDetailView: (String) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    val favorites by favoritesViewModel.favorites.collectAsStateWithLifecycle()
    FavoritesContent(
        favorites = favorites,
        toDetailView = toDetailView,
        deleteFavorite = { favoritesViewModel.deleteFavorite(it) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesContent(
    favorites: List<FavoritePlace>,
    toDetailView: (String) -> Unit,
    deleteFavorite: (FavoritePlace) -> Unit,
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
                    EmptyListMessageIcon(R.string.no_favorites_yet)
                } else {
                    LazyColumn {
                        items(
                            items = favorites,
                            key = { favorite -> favorite.favoriteId }) { favorite ->
                            SwipeBox(
                                modifier = Modifier.animateItemPlacement(),
                                onDelete = { deleteFavorite(favorite) },
                            ) {
                                FavoriteItem(favorite = favorite, onRowClick = toDetailView)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val swipeState = rememberSwipeToDismissBoxState()
    var icon: ImageVector? = null
    var alignment: Alignment = Alignment.Center
    var color: Color = Color.White

    if (swipeState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        icon = Icons.Outlined.Delete
        alignment = Alignment.CenterEnd
        color = MaterialTheme.colorScheme.error
    }

    SwipeToDismissBox(
        enableDismissFromStartToEnd = false,
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = { BackgroundContent(alignment, color, icon) }
    ) { content() }

    if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart) {
        onDelete()
    }
}

@Composable
fun BackgroundContent(alignment: Alignment, color: Color, icon: ImageVector?) {
    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.minimumInteractiveComponentSize(),
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}