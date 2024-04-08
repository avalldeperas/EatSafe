package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.annotation.StringRes
import edu.uoc.avalldeperas.eatsafe.R

sealed class Feature(val route: String, @StringRes val resourceId: Int) {
    data object Auth : Feature("auth", R.string.app_name)
    data object Home : Feature("home", R.string.app_name)
}