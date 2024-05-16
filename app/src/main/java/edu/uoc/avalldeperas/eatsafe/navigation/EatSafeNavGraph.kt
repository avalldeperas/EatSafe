package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.uoc.avalldeperas.eatsafe.explore.presentation.HomeScreen

@Composable
fun EatSafeNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val startDestination = getStartDestination()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            startDestination = Screen.Login.route,
            route = Feature.Auth.route
        ) {
            authGraph(navController = navController)
        }

        navigation(
            startDestination = Screen.ExploreHome.route,
            route = Feature.Home.route
        ) {
            composable(route = Screen.ExploreHome.route) {
                HomeScreen(authNavController = navController)
            }
        }
    }
}
fun getStartDestination(): String {
    val currentUser = Firebase.auth.currentUser
    return if (currentUser != null) Feature.Home.route else Feature.Auth.route
}
