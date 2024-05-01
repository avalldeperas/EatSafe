package edu.uoc.avalldeperas.eatsafe.ui.auth

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation.ForgotPasswordContent
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation.state.ForgotPasswordState
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.FORGOT_PASSWORD_INFO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_BACK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_EMAIL_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ForgotPasswordScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val sendEmailLbl = "Send email"
    private val forgotHeaderStr = "Forgot password?"

    private fun buildScreen(forgotState: ForgotPasswordState) {
        rule.setContent {
            ForgotPasswordContent(
                forgotState = forgotState,
                navigateBack = {},
                onUpdateEmail = {},
                onForgotClick = {}
            )
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun forgotPasswordScreen_whenStartScreen_thenExpectedEmptyValues() {
        buildScreen(ForgotPasswordState())

        rule.waitUntilAtLeastOneExists(hasText(forgotHeaderStr))
        rule.onNodeWithContentDescription(FORGOT_BACK).assertIsDisplayed()
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithTag(FORGOT_PASSWORD_INFO).assertIsDisplayed()
        rule.onNodeWithContentDescription(FORGOT_EMAIL_TEXT_FIELD).assertTextContains("E-mail")
        rule.onNodeWithText(sendEmailLbl).assertHasClickAction()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun forgotPasswordScreen_whenValuesAdded_thenExpectedValues() {
        val anEmail = "email@email.com"
        buildScreen(ForgotPasswordState(email = anEmail))

        rule.waitUntilAtLeastOneExists(hasText(forgotHeaderStr))
        rule.onNodeWithContentDescription(FORGOT_EMAIL_TEXT_FIELD).assertTextContains(anEmail)
        rule.onNodeWithText(sendEmailLbl).assertIsDisplayed().assertHasClickAction()
    }
}