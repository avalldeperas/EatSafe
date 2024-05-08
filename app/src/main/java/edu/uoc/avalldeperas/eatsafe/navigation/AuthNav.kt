package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import edu.uoc.avalldeperas.eatsafe.auth.presentation.forgot_password.ForgotPasswordScreen
import edu.uoc.avalldeperas.eatsafe.auth.presentation.login.LoginScreen
import edu.uoc.avalldeperas.eatsafe.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {

    composable(route = Screen.Login.route) {
        LoginScreen(
            toForgotPassword = { navController.navigate(route = Screen.ForgotPassword.route) },
            toRegister = { navController.navigate(route = Screen.Register.route) },
            onSubmit = { navController.navigate(route = Feature.Home.route) }
        )
    }

    composable(route = Screen.Register.route) {
        RegisterScreen(
            toLogin = { navController.popBackStack() },
            toExplore = { navController.navigate(route = Feature.Home.route) }
        )
    }

    composable(route = Screen.ForgotPassword.route) {
        ForgotPasswordScreen(
            navigateBack = { navController.popBackStack() }
        )
    }
}