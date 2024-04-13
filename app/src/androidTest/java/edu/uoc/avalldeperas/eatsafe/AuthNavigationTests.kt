package edu.uoc.avalldeperas.eatsafe

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import edu.uoc.avalldeperas.eatsafe.navigation.EatSafeNavGraph

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class AuthNavigationTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            EatSafeNavGraph(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onNodeWithText("Login Screen")
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "login")
    }

    @Test
    fun appNavHost_clickToForgotPassword_navigateToForgotPassword() {
        composeTestRule.onNodeWithContentDescription("forgot-password-link")
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "forgot_password")
    }

    @Test
    fun appNavHost_clickRegister_navigateToRegister() {
        composeTestRule.onNodeWithContentDescription("signup-link")
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "register")
    }
}