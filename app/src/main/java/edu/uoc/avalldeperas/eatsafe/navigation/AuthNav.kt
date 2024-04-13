package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import edu.uoc.avalldeperas.eatsafe.auth.presentation.ForgotPasswordScreen
import edu.uoc.avalldeperas.eatsafe.auth.presentation.LoginScreen
import edu.uoc.avalldeperas.eatsafe.auth.presentation.RegisterScreen

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
            onSubmit = { navController.navigate(route = Feature.Home.route) }
        )
    }

    composable(route = Screen.ForgotPassword.route) {

        ForgotPasswordScreen(
            navigateBack = { navController.popBackStack() },
            onSubmit = {}
        )
    }
}