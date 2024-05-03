package edu.uoc.avalldeperas.eatsafe.ui.profile

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ALLERGY_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_BACK_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_EMAIL
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_FULL_NAME
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_USERNAME
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LOGOUT_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.USER_LOCATION_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.EditProfileContent
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.state.EditProfileState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditProfileScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private lateinit var uiState: EditProfileState

    private fun buildScreen(editProfileState: EditProfileState) {
        rule.setContent { EditProfileContent(uiState = editProfileState, {}, {}, {}, {}, {}, {}) }
    }

    @Before
    fun setup() {
        uiState = EditProfileState(
            email = "anemail@email.com",
            username = "username",
            currentCity = "A city"
        )
    }

    @Test
    fun editProfileScreen_whenFirstTimeForUser_thenDisplaysEmptyValues() {
        buildScreen(uiState)

        rule.onNodeWithContentDescription(EDIT_PROFILE_BACK_ICON).assertIsDisplayed()
        rule.onNodeWithContentDescription(LOGOUT_ICON).assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_IMAGE).assertIsDisplayed()

        rule.onNodeWithText("Intolerances").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Gluten").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").assertIsDisplayed()

        rule.onNodeWithText("Personal Details").assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_EMAIL).assertIsDisplayed()
        rule.onNodeWithText("anemail@email.com").assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_USERNAME).assertIsDisplayed()
        rule.onNodeWithText("username").assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_FULL_NAME).assertIsDisplayed()
        rule.onNodeWithText("Your name").assertIsDisplayed()
        rule.onNodeWithContentDescription(USER_LOCATION_TEXT_FIELD).assertIsDisplayed()
        rule.onNodeWithText("A city").assertIsDisplayed()
        rule.onNodeWithText("Save").assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun editProfileScreen_whenHasProfileValues_thenDisplaysValues() {
        val uiState = uiState.copy(
            intolerances = mutableListOf("Gluten", "Lactose"),
            displayName = "A display name",
            currentCity = "Another city"
        )
        buildScreen(uiState)

        rule.onNodeWithContentDescription(EDIT_PROFILE_BACK_ICON).assertIsDisplayed()
        rule.onNodeWithContentDescription(LOGOUT_ICON).assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithText("Intolerances").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Gluten").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").assertIsDisplayed()

        rule.onNodeWithContentDescription(EDIT_PROFILE_EMAIL).assertIsDisplayed()
        rule.onNodeWithText("anemail@email.com").assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_FULL_NAME).assertIsDisplayed()
        rule.onNodeWithText("A display name").assertIsDisplayed()
        rule.onNodeWithContentDescription(USER_LOCATION_TEXT_FIELD).assertIsDisplayed()
        rule.onNodeWithText("Another city").assertIsDisplayed()
        rule.onNodeWithText("Save").assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun editProfileScreen_whenIsLoading_thenDisplaysLoadingMessage() {
        buildScreen(uiState.copy(isLoading = true))

        rule.onNodeWithContentDescription(EDIT_PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithText("Save").assertIsNotDisplayed()
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
    }
}