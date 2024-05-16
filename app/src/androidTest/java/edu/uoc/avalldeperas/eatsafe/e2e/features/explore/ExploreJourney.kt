package edu.uoc.avalldeperas.eatsafe.e2e.features.explore

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.GOOGLE_MAP_VIEW
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_ITEM
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.SEARCH_BAR_EXPLORE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ALLERGY_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FILTER_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.accessApplication
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.login
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ExploreJourney {

    private lateinit var navController: TestNavHostController
    private val emptyListMessage = "No Results Found! Please try different criteria"

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
    fun userExploresPlacesInMapView() {
        accessApplication(rule)
        login(rule)
        rule.waitForIdle()
        rule.onNodeWithTag(GOOGLE_MAP_VIEW).assertIsDisplayed()
        val searchBar = rule.onNodeWithTag(SEARCH_BAR_EXPLORE)
        searchBar.performTextInput("A Place name")
        searchBar.assertTextContains("A Place name")
        searchBar.performTextClearance()

        rule.onNodeWithContentDescription(FILTER_ICON).performClick()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").performClick()

        rule.onNodeWithTag(GOOGLE_MAP_VIEW).assertIsDisplayed()
    }

    @Test
    fun userExploresPlacesInListView() {
        accessApplication(rule)
        login(rule)
        rule.waitForIdle()
        rule.onNodeWithTag(GOOGLE_MAP_VIEW).assertIsDisplayed()
        rule.onNodeWithContentDescription(LIST_MAP_TOGGLE_ICON).performClick()

        val searchBar = rule.onNodeWithTag(SEARCH_BAR_EXPLORE)
        searchBar.performTextInput("A Place name")
        rule.onNodeWithText(emptyListMessage).assertIsDisplayed()
        searchBar.performTextClearance()

        searchBar.performTextInput("Restaurant")
        rule.onNodeWithText(emptyListMessage).assertIsNotDisplayed()

        rule.onNodeWithContentDescription(FILTER_ICON).performClick()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").performClick()

        rule.onNodeWithTag(PLACE_ITEM + "place3").performClick()
        rule.waitForIdle()
        rule.onNodeWithContentDescription(PLACE_IMAGE).assertIsDisplayed()
    }
}