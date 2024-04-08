package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.annotation.StringRes
import edu.uoc.avalldeperas.eatsafe.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    data object Login : Screen("login", R.string.app_name)
    data object Register : Screen("register", R.string.app_name)
    data object ForgotPassword : Screen("forgot_password", R.string.app_name)
    data object Explore : Screen("explore", R.string.app_name)
    data object Profile : Screen("profile", R.string.app_name)
    data object Favorites : Screen("favorites", R.string.app_name)
    data object EditProfile : Screen("edit_profile", R.string.app_name)
    data object Test : Screen("test", R.string.app_name)
}