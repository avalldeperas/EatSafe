package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation.DetailViewScreen
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.ExploreListScreen
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.ExploreMapScreen
import edu.uoc.avalldeperas.eatsafe.favorites.presentation.FavoritesScreen
import edu.uoc.avalldeperas.eatsafe.profile.details.presentation.ProfileScreen
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.EditProfileScreen
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.AddReviewScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    authNavController: NavHostController
) {
    composable(route = Screen.ExploreMap.route) {
        ExploreMapScreen(
            toggleView = { navController.navigate(route = Screen.ExploreList.route) },
            toDetail = { navController.navigate(route = Screen.ExploreDetail.route) }
        )
    }

    composable(route = Screen.ExploreList.route) {
        ExploreListScreen(
            toggleView = { navController.navigate(route = Screen.ExploreMap.route) },
            toDetailView = { navController.navigate(route = Screen.ExploreDetail.route) }
        )
    }

    composable(route = Screen.ExploreDetail.route) {
        DetailViewScreen(
            navigateBack = { navController.popBackStack() },
            toAddReview = { navController.navigate(route = Screen.AddReview.route) }
        )
    }

    composable(route = Screen.AddReview.route) {
        AddReviewScreen(backToDetail = { navController.popBackStack() })
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