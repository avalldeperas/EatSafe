package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import edu.uoc.avalldeperas.eatsafe.explore.presentation.ExploreScreen
import edu.uoc.avalldeperas.eatsafe.favorites.presentation.FavoritesScreen
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.EditProfileScreen
import edu.uoc.avalldeperas.eatsafe.profile.details.presentation.ProfileScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    authNavController: NavHostController
) {
    composable(route = Screen.Explore.route) {
        ExploreScreen({})
    }

    composable(route = Screen.Favorites.route) {
        FavoritesScreen({})
    }

    composable(route = Screen.Profile.route) {
        ProfileScreen(
            toEditProfile = { navController.navigate(route = Screen.EditProfile.route) },
            toLogin = { authNavController.navigate(route = Screen.Login.route) }
        )
    }

    composable(route = Screen.EditProfile.route) {
        EditProfileScreen(toProfile = { navController.popBackStack() })
    }
}