package com.course.modularfoodcatalog.componentcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.course.core.utils.Routes
import com.course.modularfoodcatalog.R


/**
 *hrahm,26/04/2024, 05:31
 **/


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    onFavoriteClick: () -> Unit
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                route = Routes.LIST_SCREEN
            ),
            BottomBarItem(
                title = stringResource(R.string.cart),
                icon = Icons.Default.ShoppingCart,
                route = Routes.FAVORITE_SCREEN,
                isActivity = true
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                route = Routes.PROFILE_SCREEN
            ),
        )
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination?.route
        navigationItems.map {
            NavigationBarItem(modifier = Modifier.padding(0.dp),
                icon = {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp) // Adjust padding values as needed
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title
                        )
                    }
                },
                label = {
                    Text(
                        it.title,
                    )
                },
                selected = currentDestination == it.route,

                onClick = {
                    if (it.isActivity) {
                        onFavoriteClick()
                    } else {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                }
            )
        }
    }
}


data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val isActivity: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun Preview() {

//    BottomBar()

}