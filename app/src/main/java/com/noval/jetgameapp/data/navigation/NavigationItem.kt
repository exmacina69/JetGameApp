package com.noval.jetgameapp.data.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val navigationViews: NavigationViews
)