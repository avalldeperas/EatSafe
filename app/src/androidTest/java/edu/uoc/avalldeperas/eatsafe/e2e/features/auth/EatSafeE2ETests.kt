package edu.uoc.avalldeperas.eatsafe.e2e.features.auth

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.ADD_REVIEW_DESCRIPTION_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.GOOGLE_MAP_VIEW
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_ITEM
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_FAVORITE_BUTTON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_RATE_BTN
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.BOTTOM_NAV_ITEM
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_BACK_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_EMAIL
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_FULL_NAME
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAVORITE_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_PASSWORD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.REGISTER_LINK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.USER_LOCATION_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.FIRST_PLACE_ID
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_EMAIL
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_VALID_PASSWORD
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EatSafeE2ETests {

    private lateinit var navController: TestNavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EatSafeNavGraph(navController = navController)
        }
    }

    @Test
    fun fullE2ETest_existingUser() {
        // Auth
        composeRule.onNodeWithText("Welcome to EatSafe").assertIsDisplayed()
        val emailField = composeRule.onNodeWithContentDescription(EMAIL_TEXT_FIELD)
        emailField.performTextInput(TESTER_EMAIL)
        val passwdField = composeRule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD)
        passwdField.performTextInput(TESTER_VALID_PASSWORD)
        val loginBtn = composeRule.onNodeWithText("Log in")
        loginBtn.performClick()
        // Explore map
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(GOOGLE_MAP_VIEW).assertIsDisplayed()
        val listIcon = composeRule.onNodeWithContentDescription(LIST_MAP_TOGGLE_ICON)
        // Explore list
        listIcon.performClick()
        val firstPlace = composeRule.onNodeWithTag(PLACE_ITEM + FIRST_PLACE_ID)
        firstPlace.performClick()
        composeRule.waitForIdle()
        // Explore Detail
        composeRule.onNodeWithContentDescription(PLACE_IMAGE).assertIsDisplayed()
        composeRule.onNodeWithText("About").assertIsDisplayed()
        composeRule.onNodeWithText("Reviews").assertIsDisplayed()
        composeRule.onNodeWithText("Add a review").performClick()
        // Add review
        composeRule.onNodeWithText("Place 1").performClick()
        composeRule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Rating" + 3).performClick()
        composeRule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Safety" + 3).performClick()
        val description = composeRule.onNodeWithTag(ADD_REVIEW_DESCRIPTION_FIELD)
        description.performTextInput("Best experience ever, will repeat")
        composeRule.onNodeWithText("Save").performClick()
        // Back to Explore Detail
        composeRule.onNodeWithText("Reviews").assertIsDisplayed()
        // TODO: mimic firestore refresh here
//        composeRule.onNodeWithText("Best experience ever, will repeat").assertIsDisplayed()
        // favs
        composeRule.onNodeWithContentDescription(ADD_FAVORITE_BUTTON).performClick()
        composeRule.onNodeWithContentDescription(
            BOTTOM_NAV_ITEM + "Favorite",
            useUnmergedTree = true
        ).performClick()
        composeRule.onNodeWithText("Favorite").assertIsDisplayed()
        composeRule.onNodeWithContentDescription(FAVORITE_PLACE_IMAGE + FIRST_PLACE_ID)
            .assertIsDisplayed()
        // profile
        composeRule.onNodeWithContentDescription(
            BOTTOM_NAV_ITEM + "Profile",
            useUnmergedTree = true
        ).performClick()
        composeRule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
        composeRule.onNodeWithText("Hello User!").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("allergy-icon-Gluten").assertIsDisplayed()
        composeRule.onNodeWithText("My Reviews").assertIsDisplayed()
        composeRule.onNodeWithText("Best experience ever, will repeat").assertIsDisplayed()
        // edit profile
        composeRule.onNodeWithContentDescription(EDIT_PROFILE_ICON).performClick()
        composeRule.onNodeWithText("Edit Profile").assertIsDisplayed()
        composeRule.onNodeWithContentDescription(EDIT_PROFILE_IMAGE).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("allergy-icon-Gluten").performClick()
        composeRule.onNodeWithContentDescription(EDIT_PROFILE_EMAIL).assertIsNotEnabled()
        composeRule.onNodeWithContentDescription(EDIT_PROFILE_FULL_NAME).performTextInput("Tester")
        composeRule.onNodeWithContentDescription(USER_LOCATION_TEXT_FIELD)
            .performTextInput("Madrid")
        composeRule.onNodeWithText("Save").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(EDIT_PROFILE_BACK_ICON).performClick()
        // back to profile
//        composeRule.onNodeWithText("Hello Tester!").assertIsDisplayed()
    }

    @Test
    fun appNavHost_clickToForgotPassword_navigateToForgotPassword() {
        composeRule.onNodeWithContentDescription(FORGOT_PASSWORD).performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "forgot_password")
    }

    @Test
    fun appNavHost_clickRegister_navigateToRegister() {
        composeRule.onNodeWithContentDescription(REGISTER_LINK).performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "register")
    }
}