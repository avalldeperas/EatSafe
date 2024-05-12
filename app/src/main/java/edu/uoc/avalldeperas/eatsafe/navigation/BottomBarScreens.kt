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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.BOTTOM_NAV_ITEM
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN


@Composable
fun BottomNavBar(homeNavController: NavHostController) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(containerColor = Color.White) {
        BottomNavigationItem().bottomNavigationItems().forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                label = {
                    Text(item.label)
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = BOTTOM_NAV_ITEM + item.label
                    )
                },
                onClick = {
                    homeNavController.navigate(item.route) {
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