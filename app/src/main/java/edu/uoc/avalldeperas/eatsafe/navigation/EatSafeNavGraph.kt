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
import edu.uoc.avalldeperas.eatsafe.explore.HomeScreen

@Composable
fun EatSafeNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Feature.Auth.route
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
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
