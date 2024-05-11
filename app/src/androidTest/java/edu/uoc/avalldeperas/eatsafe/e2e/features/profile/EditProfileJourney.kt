package edu.uoc.avalldeperas.eatsafe.e2e.features.profile

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
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
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.accessApplication
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.login
import edu.uoc.avalldeperas.eatsafe.e2e.common.NavTestUtil.navigateTo
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EditProfileJourney {

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
    fun whenUserEditsHisProfile_thenProfileIsUpdated() {
        accessApplication(rule)
        login(rule)
        rule.waitForIdle()

        navigateTo("Profile", rule)
        rule.waitForIdle()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithText("Hello User!").assertIsDisplayed()
        rule.onNodeWithText("User since 2024").assertIsDisplayed()
        rule.onNodeWithContentDescription("allergy-icon-Gluten").assertIsDisplayed()

        rule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_ICON).performClick()
        rule.onNodeWithText("Edit Profile").assertIsDisplayed()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithContentDescription("allergy-icon-Gluten").performClick()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_EMAIL).assertIsNotEnabled()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_FULL_NAME).performTextInput("Tester")
        rule.onNodeWithContentDescription(ContentDescriptionConstants.USER_LOCATION_TEXT_FIELD)
            .performTextClearance()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.USER_LOCATION_TEXT_FIELD)
            .performTextInput("Madrid")
        rule.onNodeWithText("Save").performClick()
        rule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_BACK_ICON).performClick()
        rule.waitUntil(timeoutMillis = 5000L) {
            rule.onAllNodesWithContentDescription(ContentDescriptionConstants.PROFILE_IMAGE).fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithText("Hello Tester!").assertIsDisplayed()
        // TODO mimic firebase to update profile here
//        composeRule.onNodeWithText("Madrid").assertIsDisplayed()
        rule.onNodeWithContentDescription("allergy-icon-Gluten").assertIsDisplayed()
    }
}