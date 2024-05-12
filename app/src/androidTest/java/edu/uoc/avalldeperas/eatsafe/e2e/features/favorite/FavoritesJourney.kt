package edu.uoc.avalldeperas.eatsafe.e2e.features.favorite

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_ITEM
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_FAVORITE_BUTTON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAVORITE_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.accessApplication
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.login
import edu.uoc.avalldeperas.eatsafe.e2e.common.NavTestUtil.navigateTo
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.FIRST_PLACE_ID
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FavoritesJourney {

    private lateinit var navController: TestNavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        rule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EatSafeNavGraph(navController = navController)
        }
    }

    @Test
    fun whenUserAddsFav_thenPlaceIsDisplayedInFavList() {
        accessApplication(rule)
        login(rule)
        rule.waitForIdle()
        rule.onNodeWithContentDescription(LIST_MAP_TOGGLE_ICON).performClick()
        rule.onNodeWithTag(PLACE_ITEM + FIRST_PLACE_ID).performClick()
        rule.waitForIdle()
        rule.onNodeWithContentDescription(PLACE_IMAGE).assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_FAVORITE_BUTTON).performClick()
        navigateTo("Favorite", rule)
        rule.onNodeWithText("Favorite").assertIsDisplayed()
        rule.onNodeWithContentDescription(FAVORITE_PLACE_IMAGE + FIRST_PLACE_ID)
            .assertIsDisplayed()
    }
}