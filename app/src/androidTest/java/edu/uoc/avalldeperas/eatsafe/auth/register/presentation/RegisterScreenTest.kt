package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

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
import edu.uoc.avalldeperas.eatsafe.auth.register.presentation.state.RegisterState
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CONFIRM_PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CURRENT_CITY_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LOGIN_LINK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun buildScreen(registerState: RegisterState) {
        rule.setContent {
            RegisterContent(
                registerState = registerState,
                toLogin = {},
                onEmailChange = {},
                onPasswordChange = {},
                onConfirmPasswordChange = {},
                onCurrentCityChange = {},
                onTogglePassword = {},
                onToggleConfirmPassword = {},
                onSubmit = {}
            )
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun registerScreen_whenStartScreen_thenExpectedEmptyValues() {
        buildScreen(RegisterState())

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).assertTextContains("E-mail")
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains("Password")
        rule.onNodeWithContentDescription(CONFIRM_PASSWORD_TEXT_FIELD)
            .assertTextContains("Confirm Password")
        rule.onNodeWithContentDescription(CURRENT_CITY_TEXT_FIELD)
            .assertTextContains("Current city")
        rule.onNodeWithText("Sign up").assertHasClickAction()
        rule.onNodeWithContentDescription(LOGIN_LINK)
            .assertHasClickAction().assertTextContains("Already have an account?")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun registerScreen_whenValuesAdded_thenExpectedValues() {
        val anEmail = "email@email.com"
        val aPasswd = "123456"
        val city = "A city name"
        buildScreen(
            RegisterState(
                email = anEmail,
                password = aPasswd,
                confirmPassword = aPasswd,
                currentCity = city
            )
        )

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(EMAIL_TEXT_FIELD).assertTextContains(anEmail)
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains("••••••")
        rule.onNodeWithContentDescription(CONFIRM_PASSWORD_TEXT_FIELD).assertTextContains("••••••")
        rule.onNodeWithContentDescription(CURRENT_CITY_TEXT_FIELD).assertTextContains(city)
        rule.onNodeWithText("Sign up").assertIsDisplayed().assertHasClickAction()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun registerScreen_whenPasswordIsShown_thenPasswordIsDisplayed() {
        val passwd = "123456"
        buildScreen(
            RegisterState(
                password = passwd,
                confirmPassword = passwd,
                isPasswordShown = true,
                isConfirmPasswordShown = true
            )
        )

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithContentDescription(PASSWORD_TEXT_FIELD).assertTextContains(passwd)
        rule.onNodeWithContentDescription(CONFIRM_PASSWORD_TEXT_FIELD).assertTextContains(passwd)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun registerScreen_whenIsLoading_thenLoadingIsPresentAndSubmitBtnIsNotPresent() {
        buildScreen(RegisterState(isLoading = true))

        rule.waitUntilAtLeastOneExists(hasText("Welcome to EatSafe"))
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
        rule.onNodeWithText("Sign up").assertIsNotDisplayed()
    }
}