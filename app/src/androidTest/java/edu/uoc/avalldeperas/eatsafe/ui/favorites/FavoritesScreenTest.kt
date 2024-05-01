package edu.uoc.avalldeperas.eatsafe.ui.favorites

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAVORITE_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_LOCATION_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_PLACE_TYPE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_VIEW_HEART_ICON
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import edu.uoc.avalldeperas.eatsafe.favorites.presentation.FavoritesContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class FavoritesScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun buildScreen(favorites: MutableList<FavoritePlace> = mutableListOf()) {
        rule.setContent {
            FavoritesContent(favorites = favorites, toDetailView = {})
        }
    }

    @Test
    fun favoritesScreen_whenFavourites_thenDisplaysList() {
        val date = "30/4/2024"
        val favs = buildDummyFavs(date)
        buildScreen(favs)

        rule.onNodeWithText("Favorites").assertIsDisplayed()
        favs.forEach {
            rule.onNodeWithText(it.name).assertIsDisplayed()
            rule.onNodeWithContentDescription(FAVORITE_PLACE_IMAGE + it.placeId).assertIsDisplayed()
            rule.onNodeWithContentDescription(FAV_VIEW_HEART_ICON + it.placeId).assertIsDisplayed()
            rule.onNodeWithContentDescription(FAV_PLACE_TYPE_ICON + it.placeId).assertIsDisplayed()
            rule.onNodeWithContentDescription(FAV_LOCATION_ICON + it.placeId).assertIsDisplayed()
            rule.onNodeWithText(it.address).assertIsDisplayed()
        }
    }

    @Test
    fun favoritesScreen_whenEmptyFavourites_thenDisplaysMessage() {
        buildScreen()

        rule.onNodeWithText("Favorites").assertIsDisplayed()
        rule.onNodeWithText("There are no favorites yet, start exploring!").assertIsDisplayed()
    }

    private fun buildDummyFavs(date: String): MutableList<FavoritePlace> {
        return MutableList(5) {
            FavoritePlace(
                favoriteId = "fav$it",
                placeId = "placeId$it",
                name = "placeName$it",
                address = "address$it",
                date = Timestamp(SimpleDateFormat("dd/M/yyyy").parse(date)!!)
            )
        }
    }
}