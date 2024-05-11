package edu.uoc.avalldeperas.eatsafe.e2e.common

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_EMAIL
import edu.uoc.avalldeperas.eatsafe.e2e.data.constants.TestConstants.TESTER_VALID_PASSWORD

object CommonSteps {

    fun login(
        rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        email: String = TESTER_EMAIL,
        password: String = TESTER_VALID_PASSWORD,
    ) {
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).performTextClearance()
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).performTextInput(email)
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).performTextClearance()
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).performTextInput(password)
        rule.onNodeWithText("Log in").performClick()
    }

    fun accessApplication(composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
        composeRule.waitUntil(timeoutMillis = 5000L) {
            composeRule.onAllNodesWithText("Welcome to EatSafe").fetchSemanticsNodes().isNotEmpty()
        }
    }
}