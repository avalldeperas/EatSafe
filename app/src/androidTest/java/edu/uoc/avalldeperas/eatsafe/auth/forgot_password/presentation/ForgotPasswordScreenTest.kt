package edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation.state.ForgotPasswordState
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_BACK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_EMAIL_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgotPasswordScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private lateinit var infoString: String
    private lateinit var forgotHeader: String
    private lateinit var sendEmailString: String

    private fun buildScreen(forgotState: ForgotPasswordState) {
        rule.setContent {
            ForgotPasswordContent(
                forgotState = forgotState,
                navigateBack = {},
                onUpdateEmail = {},
                onForgotClick = {}
            )
        }
        infoString = rule.activity.getString(R.string.forgot_password_info)
        forgotHeader = rule.activity.getString(R.string.forgot_password)
        sendEmailString = rule.activity.getString(R.string.send_email)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun forgotPasswordScreen_whenStartScreen_thenExpectedEmptyValues() {
        buildScreen(ForgotPasswordState())

        rule.waitUntilAtLeastOneExists(hasText(forgotHeader))
        rule.onNodeWithContentDescription(FORGOT_BACK).assertIsDisplayed()
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithText(infoString).assertIsDisplayed()
        rule.onNodeWithContentDescription(FORGOT_EMAIL_TEXT_FIELD).assertTextContains("E-mail")
        rule.onNodeWithText(sendEmailString).assertHasClickAction()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun forgotPasswordScreen_whenValuesAdded_thenExpectedValues() {
        val anEmail = "email@email.com"
        buildScreen(ForgotPasswordState(email = anEmail))

        rule.waitUntilAtLeastOneExists(hasText(forgotHeader))
        rule.onNodeWithContentDescription(FORGOT_EMAIL_TEXT_FIELD).assertTextContains(anEmail)
        rule.onNodeWithText(sendEmailString).assertIsDisplayed().assertHasClickAction()
    }
}