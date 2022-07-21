package com.lambdadigamma.moers

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lambdadigamma.fuel.detail.FuelStationDetailScreen
import com.lambdadigamma.fuel.list.FuelStationListScreen
import com.lambdadigamma.moers.dashboard.DashboardAction
import com.lambdadigamma.moers.dashboard.DashboardScreen
import com.lambdadigamma.moers.events.ui.EventsScreen
import com.lambdadigamma.moers.explore.ExploreScreen
import com.lambdadigamma.moers.search.SearchScreen
import com.lambdadigamma.newsfeature.ui.NewsScreen
import com.lambdadigamma.newsfeature.ui.NewsWebDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.dashboard,
    modifier: Modifier = Modifier
) {

    rememberSystemUiController().setStatusBarColor(
        MaterialTheme.colorScheme.background, darkIcons = !isSystemInDarkTheme()
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier

    ) {

        // ------------------------------------------------------------

        composable(route = Destinations.dashboard) {
            DashboardScreen(onOpenSettings = {
                navController.navigate(
                    Destinations.settings,
                    navOptions = NavOptions.Builder().build(),
                )
            }, onAction = {
                if (it == DashboardAction.Rubbish) {
                    navController.navigate(
                        Destinations.rubbishList
                    )
                } else if (it == DashboardAction.Fuel) {
                    navController.navigate(
                        Destinations.fuelList
                    )
                }
            })
        }

        composable(route = Destinations.rubbishList) {
            RubbishListScreen()
        }

        composable(route = Destinations.fuelList) {
            FuelStationListScreen(onShowFuelStation = { id ->
                navController.navigate(
                    Destinations.fuelStationDetail.replace(
                        "{id}",
                        id
                    ),
                    navOptions = NavOptions.Builder().build(),
                )
            })
        }

        composable(
            route = Destinations.fuelStationDetail,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")

            id?.let {
                FuelStationDetailScreen(id = it)
            }

        }

        // ------------------------------------------------------------


        composable(route = Destinations.news) {
            NewsScreen(onShowRssItem = {
                navController.navigate(
                    Destinations.showNewsWebDetail.replace(
                        "{id}",
                        it.toString()
                    )
                )
            })
        }
        composable(route = Destinations.explore) {
            ExploreScreen()
        }
        composable(route = Destinations.search) {
            SearchScreen()
        }
        composable(route = Destinations.events) {
            EventsScreen()
        }

        composable(route = Destinations.settings) {
            SettingsScreen(onBack = {
                navController.popBackStack()
            })
        }

        composable(
            route = Destinations.showNewsWebDetail,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")

            id?.let {
                NewsWebDetailScreen(
                    id = id,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}
