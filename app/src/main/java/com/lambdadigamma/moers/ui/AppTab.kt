package com.lambdadigamma.moers.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.lambdadigamma.moers.Destinations
import com.lambdadigamma.moers.R

val t = Icons.Filled.Home

enum class AppTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val inactiveIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
) {
    DASHBOARD(
        R.string.navigation_dashboard,
        R.drawable.ic_baseline_dashboard_24,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        Destinations.dashboard
    ),
    EXPLORE(
        R.string.navigation_explore,
        R.drawable.ic_baseline_explore_24,
        Icons.Filled.PlayArrow,
        Icons.Filled.PlayArrow,
        Destinations.explore
    ),
    SEARCH(
        R.string.navigation_search,
        R.drawable.ic_baseline_search_24,
        Icons.Outlined.Search,
        Icons.Filled.Search,
        Destinations.search
    )
//    OTHER(
//        R.string.navigation_other,
//        R.drawable.ic_baseline_menu_24,
//        Destinations.other
//    ),
}