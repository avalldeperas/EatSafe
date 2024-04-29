package edu.uoc.avalldeperas.eatsafe.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.BOTTOM_NAV_ITEM
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN


@Composable
fun BottomNavBar(homeNavController: NavHostController) {
    var navigationSelectedItem by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(containerColor = Color.White) {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = BOTTOM_NAV_ITEM + navigationItem.label
                    )
                },
                onClick = {
                    navigationSelectedItem = index
                    homeNavController.navigate(navigationItem.route) {
                        popUpTo(homeNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MAIN_GREEN,
                    selectedTextColor = MAIN_GREEN,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Explore",
                icon = Icons.Outlined.Explore,
                route = Screen.ExploreMap.route
            ),
            BottomNavigationItem(
                label = "Favorite",
                icon = Icons.Filled.Favorite,
                route = Screen.Favorites.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.AccountCircle,
                route = Screen.Profile.route
            )
        )
    }
}