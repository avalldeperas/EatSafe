package edu.uoc.avalldeperas.eatsafe.ui.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ALLERGY_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_REVIEW_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.profile.details.presentation.ProfileContent
import edu.uoc.avalldeperas.eatsafe.profile.details.presentation.state.ProfileState
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat


@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val aDate = "30/4/2021"
    private val aUser = User(
        dateJoined = Timestamp(SimpleDateFormat("dd/M/yyyy").parse(aDate)!!),
        currentCity = "A city name",
        username = "username"
    )

    private fun buildScreen(profileState: ProfileState, displayName: String = "") {
        rule.setContent {
            ProfileContent(profileState = profileState, {}, { displayName })
        }
    }

    @Test
    fun profileScreen_whenFirstTimeForUser_thenDisplaysEmptyValues() {
        buildScreen(ProfileState(user = aUser), displayName = aUser.username)

        rule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_ICON).assertIsDisplayed()
        rule.onNodeWithText("Hello username!").assertIsDisplayed()
        rule.onNodeWithText("User since 2021").assertIsDisplayed()
        rule.onNodeWithText("A city name").assertIsDisplayed()
        rule.onNodeWithText("Intolerances").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Gluten").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").assertIsDisplayed()
        rule.onNodeWithText("My Reviews").assertIsDisplayed()
        rule.onNodeWithContentDescription(EATSAFE_LOGO).assertIsDisplayed()
        rule.onNodeWithText("There are no reviews yet, share your experience!").assertIsDisplayed()
    }

    @Test
    fun profileScreen_whenHasProfileValues_thenDisplaysValues() {
        val date = "/5/2024"
        val reviews = dummyReviews(date)
        buildScreen(ProfileState(user = aUser, reviews = reviews), displayName = "DisplayName")

        rule.onNodeWithContentDescription(PROFILE_IMAGE).assertIsDisplayed()
        rule.onNodeWithContentDescription(EDIT_PROFILE_ICON).assertIsDisplayed()

        rule.onNodeWithText("Hello DisplayName!").assertIsDisplayed()
        rule.onNodeWithText("User since 2021").assertIsDisplayed()
        rule.onNodeWithText("A city name").assertIsDisplayed()
        rule.onNodeWithText("Intolerances").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Gluten").assertIsDisplayed()
        rule.onNodeWithContentDescription(ALLERGY_ICON + "Lactose").assertIsDisplayed()
        rule.onNodeWithText("My Reviews").assertIsDisplayed()
        rule.onNodeWithText("There are no reviews yet, share your experience!")
            .assertIsNotDisplayed()
        repeat(reviews.size) {
            rule.onNodeWithText("place name $it").assertIsDisplayed()
            rule.onNodeWithText("${it+1}$date").assertIsDisplayed()
            rule.onNodeWithContentDescription(PROFILE_REVIEW_PLACE_IMAGE + "placeId$it")
                .assertIsDisplayed()
            rule.onNodeWithText("A review description $it").assertIsDisplayed()
        }
    }

    @Test
    fun profileScreen_whenIsLoading_thenDisplaysLoadingMessage() {
        buildScreen(ProfileState(isLoading = true))

        rule.onNodeWithTag(PROFILE_IMAGE).assertIsNotDisplayed()
        rule.onNodeWithText("Loading…").assertIsDisplayed()
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
    }

    private fun dummyReviews(date: String): List<Review> {
        return List(5) {
            Review(
                reviewId = "id$it",
                placeId = "placeId$it",
                placeName = "place name $it",
                rating = it,
                safety = it,
                description = "A review description $it",
                date = Timestamp(SimpleDateFormat("d/M/yyyy").parse("${it + 1}$date")!!)
            )
        }
    }
}