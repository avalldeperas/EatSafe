package edu.uoc.avalldeperas.eatsafe.e2e.features.review

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.ADD_REVIEW_DESCRIPTION_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_ITEM
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_RATE_BTN
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_MAP_TOGGLE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.accessApplication
import edu.uoc.avalldeperas.eatsafe.e2e.common.CommonSteps.login
import edu.uoc.avalldeperas.eatsafe.e2e.common.NavTestUtil.navigateTo
import edu.uoc.avalldeperas.eatsafe.e2e.common.TestConstants.FIRST_PLACE_ID
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ReviewsJourney {

    private lateinit var navController: TestNavHostController
    private val aDescription = "Best experience ever, will repeat"

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        rule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EatSafeNavGraph(navController = navController)
        }
    }

    @Test
    fun whenUserAddsAReview_thenReviewIsDisplayedInPlaceDetailAndInProfile() {
        accessApplication(rule)
        login(rule)
        rule.waitForIdle()
        rule.onNodeWithContentDescription(LIST_MAP_TOGGLE_ICON).performClick()
        rule.onNodeWithTag(PLACE_ITEM + FIRST_PLACE_ID).performClick()
        rule.waitForIdle()
        rule.onNodeWithContentDescription(PLACE_IMAGE).assertIsDisplayed()
        rule.onNodeWithText("Reviews").assertIsDisplayed()

        rule.onNodeWithText("Add a review").performClick()
        rule.onNodeWithText("Place 1").assertIsDisplayed()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Safety" + 4).performClick()
        rule.onNodeWithContentDescription(ADD_REVIEW_RATE_BTN + "Rating" + 2).performClick()
        rule.onNodeWithTag(ADD_REVIEW_DESCRIPTION_FIELD).performTextInput(aDescription)
        rule.onNodeWithText("Save").performClick()

        rule.onNodeWithText("Reviews").assertIsDisplayed()
        // TODO: mimic firestore refresh here
//        composeRule.onNodeWithText("Best experience ever, will repeat").assertIsDisplayed()
//        composeRule.onNodeWithText("Add a review").assertIsNotDisplayed()
        navigateTo("Profile", rule)
        rule.waitForIdle()
        rule.onNodeWithText("My Reviews").assertIsDisplayed()
        rule.onNodeWithText(aDescription).assertIsDisplayed()
    }
}