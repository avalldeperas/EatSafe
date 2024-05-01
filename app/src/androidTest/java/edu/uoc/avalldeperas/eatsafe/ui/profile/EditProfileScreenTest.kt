package edu.uoc.avalldeperas.eatsafe.ui.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.state.EditProfileState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditProfileScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private fun buildScreen(editProfileState: EditProfileState) {
//        rule.setContent { ProfileContent(profileState = editProfileState) }
    }

    @Test
    fun profileScreen_whenFirstTimeForUser_thenDisplaysEmptyValues() {
        buildScreen(EditProfileState())

        rule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
    }

    @Test
    fun profileScreen_whenHasProfileValues_thenDisplaysValues() {

        buildScreen(EditProfileState())

        rule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
    }

    @Test
    fun profileScreen_whenIsLoading_thenDisplaysLoadingMessage() {
        buildScreen(EditProfileState(isLoading = true))

        rule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
    }
}