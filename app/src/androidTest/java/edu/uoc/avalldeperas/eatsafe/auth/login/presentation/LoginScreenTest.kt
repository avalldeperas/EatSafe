package edu.uoc.avalldeperas.eatsafe.auth.login.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.auth.login.presentation.state.LoginState
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.REGISTER_LINK
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun buildScreen(loginState: LoginState) {
        rule.setContent {
            LoginContent(
                loginState = loginState,
                toForgotPassword = {},
                toRegister = {},
                onSubmit = {},
                onUpdatePassword = {},
                onEmailChange = {},
                onToggleVisualTransformation = {}
            )
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loginScreen_whenStartScreen_thenExpectedEmptyValues() {
        buildScreen(LoginState())

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).assertTextContains("E-mail")
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains("Password")
        rule.onNodeWithText("Log in").assertHasClickAction()
        rule.onNodeWithContentDescription(REGISTER_LINK).assertHasClickAction().assertTextContains("Don't have an account?")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loginScreen_whenValuesAdded_thenExpectedValues() {
        val randomEmail = "email@email.com"
        val randomPassword = "123456"
        buildScreen(LoginState(email = randomEmail, password = randomPassword))

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).assertTextContains(randomEmail)
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains("••••••")
        rule.onNodeWithText("Log in").assertIsDisplayed().assertHasClickAction()
        rule.onNodeWithContentDescription(REGISTER_LINK).assertIsDisplayed().assertHasClickAction()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loginScreen_whenPasswordIsShown_thenPasswordIsDisplayed() {
        val randomPassword = "123456"
        buildScreen(LoginState(password = randomPassword, isPasswordShown = true))

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains(randomPassword)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loginScreen_whenIsLoading_thenLoadingIsPresentAndSubmitBtnIsNotPresent() {
        buildScreen(LoginState(isLoading = true))

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
        rule.onNodeWithText("Log in").assertIsNotDisplayed()
    }
}