package edu.uoc.avalldeperas.eatsafe.e2e.features.auth

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.FORGOT_PASSWORD_INFO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CONFIRM_PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CURRENT_CITY_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_BACK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_PASSWORD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.REGISTER_LINK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.SHOW_PASSWORD_ICON_FIELD
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.accessApplication
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.login
import edu.uoc.avalldeperas.eatsafe.e2e.common.NavTestUtil.validateCurrentRoute
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_EMAIL
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_VALID_PASSWORD
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AuthTests {

    private lateinit var navController: TestNavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    private val sendEmailLbl = "Send email"
    private val forgotHeaderStr = "Forgot password?"

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
    fun whenNewUserRegisters_thenExploreScreenIsDisplayed() {
        accessApplication(rule)
        rule.onNodeWithContentDescription(REGISTER_LINK).assertHasClickAction()
            .assertTextContains("Don't have an account?").performClick()

        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).performTextInput("valid@email.com")
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).performTextInput("password")
        showPassword(rule, PASSWORD_TEXT_FIELD)
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains("password")
        rule.onNodeWithContentDescription(CONFIRM_PASSWORD_TEXT_FIELD).performTextInput("password")
        showPassword(rule, CONFIRM_PASSWORD_TEXT_FIELD)
        rule.onNodeWithContentDescription(CONFIRM_PASSWORD_TEXT_FIELD)
            .assertTextContains("password")
        rule.onNodeWithContentDescription(CURRENT_CITY_TEXT_FIELD).performTextInput("Barcelona")
        rule.onNodeWithText("Sign up").performClick()
        rule.waitForIdle()

        validateCurrentRoute(navController, "explore_home")
    }

    @Test
    fun whenUserLogins_thenAbleToAccessApp() {
        accessApplication(rule)

        login(rule, email = TESTER_EMAIL, password = "invalid_passwd")
        validateCurrentRoute(navController, "login")

        login(rule, email = TESTER_EMAIL, password = TESTER_VALID_PASSWORD)
        validateCurrentRoute(navController, "explore_home")
    }

    @Test
    fun whenUserResetsPassword_thenAbleToLogin() {
        accessApplication(rule)
        rule.onNodeWithContentDescription(FORGOT_PASSWORD).performClick()
        validateCurrentRoute(navController, "forgot_password")
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithText(forgotHeaderStr).assertIsDisplayed()
        rule.onNodeWithTag(FORGOT_PASSWORD_INFO).assertIsDisplayed()
        rule.onNodeWithContentDescription(FORGOT_EMAIL_TEXT_FIELD).performTextInput(TESTER_EMAIL)
        rule.onNodeWithText(sendEmailLbl).performClick()
        rule.waitForIdle()

        rule.onNodeWithContentDescription(FORGOT_BACK).performClick()
        rule.onNodeWithText("Welcome to EatSafe").assertIsDisplayed()
        login(rule, password = TESTER_VALID_PASSWORD)
        validateCurrentRoute(navController, "explore_home")
    }

    private fun showPassword(
        rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        contentDescription: String,
    ) {
        rule.onNodeWithContentDescription(SHOW_PASSWORD_ICON_FIELD + contentDescription)
            .performClick()
    }
}