package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.annotation.StringRes
import edu.uoc.avalldeperas.eatsafe.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    data object Login : Screen("login", R.string.app_name)
    data object Register : Screen("register", R.string.app_name)
    data object ForgotPassword : Screen("forgot_password", R.string.app_name)
    data object Profile : Screen("profile", R.string.app_name)
    data object Favorites : Screen("favorites", R.string.app_name)
    data object EditProfile : Screen("edit_profile", R.string.app_name)
    data object ExploreList : Screen("explore_list", R.string.app_name)
    data object ExploreMap : Screen("explore_map", R.string.app_name)
    data object ExploreDetail : Screen("explore_detail", R.string.app_name)
    data object AddReview : Screen("add_review", R.string.app_name)
    data object ExploreHome : Screen("explore_home", R.string.app_name)
}

object Constants {
    const val PLACE_ID_PARAM = "placeId"
    const val PLACE_NAME_PARAM = "placeName"
}