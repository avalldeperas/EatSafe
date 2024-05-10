package edu.uoc.avalldeperas.eatsafe.e2e.common

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.e2e.common.NavTestUtil.navigateTo
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants

object CommonSteps {

    fun login(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        val emailField =
            composeRule.onNodeWithContentDescription(ContentDescriptionConstants.EMAIL_TEXT_FIELD)
        emailField.performTextInput(TestConstants.TESTER_EMAIL)
        val passwdField =
            composeRule.onNodeWithContentDescription(ContentDescriptionConstants.PASSWORD_TEXT_FIELD)
        passwdField.performTextInput(TestConstants.TESTER_VALID_PASSWORD)
        val loginBtn = composeRule.onNodeWithText("Log in")
        loginBtn.performClick()
    }

    fun logout(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        navigateTo("Profile", composeRule)
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(ContentDescriptionConstants.EDIT_PROFILE_ICON)
            .performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithContentDescription(ContentDescriptionConstants.LOGOUT_ICON)
            .performClick()
    }

    fun accessApplication(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        composeRule.waitUntil(timeoutMillis = 5000L) {
            composeRule.onAllNodesWithText("Welcome to EatSafe").fetchSemanticsNodes().isNotEmpty()
        }
    }
}