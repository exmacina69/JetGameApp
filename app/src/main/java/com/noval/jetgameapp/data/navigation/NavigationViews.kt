package com.noval.jetgameapp.data.navigation

sealed class NavigationViews(val route: String) {
    object Home : NavigationViews("home")
    object Favorite : NavigationViews("favorite")
    object About : NavigationViews("about")
    object DetailGame : NavigationViews("home/{gamesId}") {
        fun createRoute(gamesId: String) = "home/$gamesId"
    }
}