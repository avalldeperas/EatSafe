package edu.uoc.avalldeperas.eatsafe.ui.reviews

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.ADD_REVIEW_DESCRIPTION_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_BACK_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_RATE_BTN
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.AddReviewContent
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.AddReviewState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddReviewScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val placeholder =
        "Describe your experience with this establishment between 20 word and 100 words."

    private fun buildScreen(detailState: AddReviewState) {
        rule.setContent {
            AddReviewContent(detailState, {}, {}, {}, {}, {})
        }
    }

    @Test
    fun addReviewScreen_whenFirstTime_thenDisplaysEmptyValues() {
        buildScreen(AddReviewState(review = Review(placeName = "Place name")))

        rule.waitUntil {
            rule.onAllNodesWithText("Add a review").fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithContentDescription(ADD_REVIEW_BACK_ICON).assertIsDisplayed()
        rule.onNodeWithText("Safety").assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Safety" + "0").assertIsEnabled()
        rule.onNodeWithText("Rating").assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Rating" + "0").assertIsEnabled()
        rule.onNodeWithText("Description").assertIsDisplayed()
        rule.onNodeWithTag(ADD_REVIEW_DESCRIPTION_FIELD).assertTextContains(placeholder)
        rule.onNodeWithContentDescription(CIRCULAR_PROGRESS_TAG).isNotDisplayed()
        rule.onNodeWithText("Save").assertHasClickAction()
    }

    @Test
    fun addReviewScreen_whenUserInputsValues_thenDisplaysValues() {
        val review = Review(placeName = "Place name")
        buildScreen(
            AddReviewState(
                description = "This is a description.", safety = 4, rating = 3, review = review
            )
        )

        rule.waitUntil {
            rule.onAllNodesWithText("Add a review").fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithContentDescription(ADD_REVIEW_BACK_ICON).assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Safety" + "3").assertIsEnabled()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Rating" + "2").assertIsEnabled()
        rule.onNodeWithTag(ADD_REVIEW_DESCRIPTION_FIELD)
            .assertTextContains("This is a description.")
        rule.onNodeWithText("Save").assertIsDisplayed().assertHasClickAction()
    }

    @Test
    fun addReviewScreen_whenIsLoading_thenDisplaysProgressBar() {
        buildScreen(AddReviewState(review = Review(placeName = "Place name"), isLoading = true))

        rule.waitUntil {
            rule.onAllNodesWithText("Add a review").fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithContentDescription(ADD_REVIEW_BACK_ICON).assertIsDisplayed()
        rule.onNodeWithText("Safety").assertIsDisplayed()
        rule.onNodeWithText("Rating").assertIsDisplayed()
        rule.onNodeWithText("Description").assertIsDisplayed()
        rule.onNodeWithTag(ADD_REVIEW_DESCRIPTION_FIELD).assertIsDisplayed()
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
        rule.onNodeWithText("Save").assertIsNotDisplayed()
    }
}