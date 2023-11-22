package com.noval.jetgameapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.noval.jetgameapp.data.navigation.NavigationItem
import com.noval.jetgameapp.data.navigation.NavigationViews
import com.noval.jetgameapp.ui.views.about.AboutScreen
import com.noval.jetgameapp.ui.views.detail.DetailScreen
import com.noval.jetgameapp.ui.views.favorite.FavoriteScreen
import com.noval.jetgameapp.ui.views.home.HomeScreen

@Composable
fun JetGameApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != NavigationViews.DetailGame.route) {
                if (currentRoute != NavigationViews.Favorite.route) {
                    if (currentRoute != NavigationViews.About.route) {
                        BottomBar(navController = navController)
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationViews.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = NavigationViews.Home.route
            ) {
                HomeScreen(navigateToDetail = { gamesId ->
                    navController.navigate(NavigationViews.DetailGame.createRoute(gamesId))
                })
            }
            composable(
                route = NavigationViews.DetailGame.route,
                arguments = listOf(navArgument("gamesId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("gamesId") ?: ""
                DetailScreen(
                    gamesId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(
                route = NavigationViews.Favorite.route
            ) {
                FavoriteScreen(
                    navigateBack = { navController.navigateUp() },
                    navigateToDetail = { gamesId ->
                        navController.navigate(NavigationViews.DetailGame.createRoute(gamesId))
                    }
                )
            }
            composable(
                route = NavigationViews.About.route
            ) {
                AboutScreen(
                    navigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                navigationViews = NavigationViews.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                navigationViews = NavigationViews.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.Person,
                navigationViews = NavigationViews.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.navigationViews.route,
                onClick = {
                    navController.navigate(item.navigationViews.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

