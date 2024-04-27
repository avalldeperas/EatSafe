package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation.DetailViewScreen
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.ExploreListScreen
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.ExploreMapScreen
import edu.uoc.avalldeperas.eatsafe.explore.list_map.presentation.ExploreViewModel
import edu.uoc.avalldeperas.eatsafe.favorites.presentation.FavoritesScreen
import edu.uoc.avalldeperas.eatsafe.navigation.Constants.PLACE_ID_PARAM
import edu.uoc.avalldeperas.eatsafe.navigation.Constants.PLACE_NAME_PARAM
import edu.uoc.avalldeperas.eatsafe.profile.details.presentation.ProfileScreen
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.EditProfileScreen
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.AddReviewScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    authNavController: NavHostController,
    exploreViewModel: ExploreViewModel
) {
    composable(route = Screen.ExploreMap.route) {
        ExploreMapScreen(
            toggleView = { navController.navigate(route = Screen.ExploreList.route) },
            toDetailView = { placeId ->
                navController.navigate(route = Screen.ExploreDetail.route + "/$placeId")
            },
            exploreViewModel = exploreViewModel
        )
    }

    composable(route = Screen.ExploreList.route) {
        ExploreListScreen(
            toggleView = { navController.navigate(route = Screen.ExploreMap.route) },
            toDetailView = { placeId ->
                navController.navigate(route = Screen.ExploreDetail.route + "/$placeId")
            },
            exploreViewModel = exploreViewModel
        )
    }

    composable(route = Screen.ExploreDetail.route + "/{$PLACE_ID_PARAM}") {
        DetailViewScreen(
            navigateBack = { navController.popBackStack() },
            toAddReview = { place ->
                navController.navigate(route = Screen.AddReview.route + "/${place.placeId}/${place.name}")
            }
        )
    }

    composable(route = Screen.AddReview.route + "/{$PLACE_ID_PARAM}/{$PLACE_NAME_PARAM}") {
        AddReviewScreen(backToDetail = { navController.popBackStack() })
    }

    composable(route = Screen.Favorites.route) {
        FavoritesScreen(toDetailView = { placeId ->
            navController.navigate(Screen.ExploreDetail.route + "/$placeId")
        })
    }

    composable(route = Screen.Profile.route) {
        ProfileScreen(
            toEditProfile = { navController.navigate(route = Screen.EditProfile.route) }
        )
    }



    composable(route = Screen.EditProfile.route) {
        EditProfileScreen(
            backToProfile = {
                navController.navigate(route = Screen.Profile.route) {
                    popUpTo(route = Screen.Profile.route) {
                        inclusive = true
                    }
                }
            },
            onLogout = { authNavController.navigate(route = Screen.Login.route) }
        )
    }
}