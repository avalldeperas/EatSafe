package edu.uoc.avalldeperas.eatsafe.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.uoc.avalldeperas.eatsafe.explore.HomeScreen

@Composable
fun EatSafeNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = getStartDestination()
    Log.d("avb", currentNavBackStackEntry?.destination?.route ?: startDestination)

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            startDestination = Screen.Login.route,
            route = Feature.Auth.route
        ) {
            authGraph(navController = navController)
        }

        navigation(
            startDestination = Screen.Test.route,
            route = Feature.Home.route
        ) {
            composable(route = Screen.Test.route) {
                HomeScreen(authNavController = navController)
            }
        }
    }
}
fun getStartDestination(): String {
    val currentUser = Firebase.auth.currentUser
    return if (currentUser != null) Feature.Home.route else Feature.Auth.route
}
