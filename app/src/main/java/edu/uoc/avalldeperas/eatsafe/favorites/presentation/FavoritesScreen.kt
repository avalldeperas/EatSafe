package edu.uoc.avalldeperas.eatsafe.favorites.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoritesScreen(toDetail: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Favorites Screen")
        IconButton(onClick = { toDetail() }) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "detail-info"
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen({})
}