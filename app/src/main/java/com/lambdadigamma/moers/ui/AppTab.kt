package com.lambdadigamma.moers.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lambdadigamma.moers.Destinations
import com.lambdadigamma.moers.R

enum class AppTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    DASHBOARD(
        R.string.navigation_dashboard,
        R.drawable.ic_baseline_dashboard_24,
        Destinations.dashboard
    ),
    EXPLORE(
        R.string.navigation_explore,
        R.drawable.ic_baseline_explore_24,
        Destinations.explore
    ),
    SEARCH(
        R.string.navigation_search,
        R.drawable.ic_baseline_search_24,
        Destinations.search
    )
//    OTHER(
//        R.string.navigation_other,
//        R.drawable.ic_baseline_menu_24,
//        Destinations.other
//    ),
}