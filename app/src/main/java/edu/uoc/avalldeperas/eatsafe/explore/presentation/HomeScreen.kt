package edu.uoc.avalldeperas.eatsafe.explore.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import edu.uoc.avalldeperas.eatsafe.explore.presentation.list_map.ExploreViewModel
import edu.uoc.avalldeperas.eatsafe.navigation.BottomNavBar
import edu.uoc.avalldeperas.eatsafe.navigation.Screen
import edu.uoc.avalldeperas.eatsafe.navigation.homeGraph

@Composable
fun HomeScreen(
    homeNavController: NavHostController = rememberNavController(),
    authNavController: NavHostController,
    sharedViewModel: ExploreViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = { BottomNavBar(homeNavController) }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = homeNavController,
            startDestination = Screen.ExploreMap.route
        ) {

            homeGraph(homeNavController, authNavController, sharedViewModel)
        }
    }
}