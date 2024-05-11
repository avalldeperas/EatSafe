package edu.uoc.avalldeperas.eatsafe.ui.explore

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_INFO_ELEMENT
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_AVG_RATING
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_AVG_SAFETY
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_TOTAL_REVIEWS
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_FAVORITE_BUTTON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.DETAIL_BACK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.USER_IMAGE_REVIEW
import edu.uoc.avalldeperas.eatsafe.explore.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.explore.presentation.detail_view.DetailViewContent
import edu.uoc.avalldeperas.eatsafe.explore.presentation.detail_view.DetailViewState
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class DetailViewScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private lateinit var detailState: DetailViewState
    private val emptyReviewsText = "There are no reviews yet, share your experience!"

    private val dummyPlace = Place(
        name = "Place name",
        telephone = "900000000",
        website = "www.website.com",
        address = "Street name, 01, City"
    )

    private fun buildScreen(detailState: DetailViewState) {
        rule.setContent {
            DetailViewContent(
                detailState = detailState,
                navigateBack = {},
                toAddReview = {},
                snackbarState = SnackbarHostState(),
                onAddFav = {}
            )
        }
    }

    @Before
    fun setup() {
        detailState = DetailViewState(place = dummyPlace)
    }

    @Test
    fun detailViewScreen_whenFirstTime_thenDisplaysEmptyValues() {
        buildScreen(detailState)

        rule.waitUntil {
            rule.onAllNodesWithText(dummyPlace.name).fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithContentDescription(DETAIL_BACK).assertIsDisplayed()
        rule.onNodeWithContentDescription(PLACE_IMAGE).assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_FAVORITE_BUTTON).assertIsDisplayed()
            .assertHasClickAction()

        rule.onNodeWithText("Ratings: ").assertIsDisplayed()
        rule.onNodeWithTag(RATING_ITEM_AVG_SAFETY).onChildAt(0).assertTextContains("0.0")
        rule.onNodeWithTag(RATING_ITEM_AVG_RATING).onChildAt(0).assertTextContains("0.0")
        rule.onNodeWithTag(RATING_ITEM_TOTAL_REVIEWS).onChildAt(0).assertTextContains("0")

        rule.onNodeWithText("About").assertIsDisplayed()
        rule.onNodeWithTag(PLACE_INFO_ELEMENT + dummyPlace.telephone)
            .assertTextEquals(dummyPlace.telephone)
        rule.onNodeWithTag(PLACE_INFO_ELEMENT + dummyPlace.website)
            .assertTextEquals(dummyPlace.website)
        rule.onNodeWithTag(PLACE_INFO_ELEMENT + dummyPlace.address)
            .assertTextEquals(dummyPlace.address)

        rule.onNodeWithText("Add a review").assertIsDisplayed().assertHasClickAction()
        rule.onNodeWithText(emptyReviewsText).assertIsDisplayed()
    }

    @Test
    fun detailViewScreen_whenPlaceIsReviewedByUser_thenReviewDisplayedAndAddReviewHidden() {
        val date = "30/4/2024"
        val review = Review(
            userName = "Tester",
            date = Timestamp(SimpleDateFormat("dd/M/yyyy").parse(date)!!),
            description = "This is a long review.",
            safety = 3,
            rating = 4
        )
        val place = dummyPlace.copy(
            averageSafety = 4.5,
            averageRating = 4.3,
            totalReviews = 1
        )
        buildScreen(detailState.copy(place = place, isUserReview = true, reviews = listOf(review)))

        rule.onNodeWithTag(RATING_ITEM_AVG_SAFETY).onChildAt(0).assertTextContains("4.5")
        rule.onNodeWithTag(RATING_ITEM_AVG_RATING).onChildAt(0).assertTextContains("4.3")
        rule.onNodeWithTag(RATING_ITEM_TOTAL_REVIEWS).onChildAt(0).assertTextContains("1")

        rule.onNodeWithText("Reviews").assertIsDisplayed()
        rule.onNodeWithText("Add a review").assertIsNotDisplayed()
        rule.onNodeWithText(emptyReviewsText).assertIsNotDisplayed()
        rule.onNodeWithText(USER_IMAGE_REVIEW).onChild()
        rule.onNodeWithText("This is a long review.").assertIsDisplayed()
        rule.onNodeWithText(date).assertIsDisplayed()
    }

    @Test
    fun detailViewScreen_whenIsLoading_thenProgressIndicatorIsShown() {
        buildScreen(detailState.copy(isLoading = true))
        rule.onNodeWithTag(CIRCULAR_PROGRESS_TAG).assertIsDisplayed()
        rule.onNodeWithContentDescription(PLACE_IMAGE).assertIsNotDisplayed()
    }
}