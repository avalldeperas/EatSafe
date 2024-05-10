package edu.uoc.avalldeperas.eatsafe.e2e.features.auth

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import edu.uoc.avalldeperas.eatsafe.MainActivity
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_PASSWORD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.REGISTER_LINK
import edu.uoc.avalldeperas.eatsafe.di.AppModule
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AuthTests {

    private lateinit var navController: TestNavHostController

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EatSafeNavGraph(navController = navController)
        }
    }

    @Test
    fun appNavHost_clickToForgotPassword_navigateToForgotPassword() {
        composeRule.onNodeWithContentDescription(FORGOT_PASSWORD).performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "forgot_password")
    }

    @Test
    fun appNavHost_clickRegister_navigateToRegister() {
        composeRule.onNodeWithContentDescription(REGISTER_LINK).performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "register")
    }
}