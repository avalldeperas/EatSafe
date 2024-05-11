package edu.uoc.avalldeperas.eatsafe.e2e.common

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import org.junit.Assert.assertEquals

object NavTestUtil {

    fun navigateTo(
        route: String,
        composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    ) {
        composeRule.onNodeWithContentDescription(
            ContentDescriptionConstants.BOTTOM_NAV_ITEM + route,
            useUnmergedTree = true
        ).performClick()
    }

    fun validateCurrentRoute(navController: NavController, route: String) {
        assertEquals(navController.currentBackStackEntry?.destination?.route, route)
    }
}