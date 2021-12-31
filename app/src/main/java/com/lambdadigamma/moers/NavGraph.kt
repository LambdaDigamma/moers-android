package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lambdadigamma.moers.dashboard.DashboardScreen
import com.lambdadigamma.moers.events.ui.EventsScreen
import com.lambdadigamma.moers.explore.ExploreScreen
import com.lambdadigamma.moers.onboarding.OnboardingWelcomeScreen
import com.lambdadigamma.moers.search.SearchScreen

object Destinations {
    object Onboarding {
        const val start = "start"
    }

    const val dashboard = "dashboard"
    const val explore = "explore"
    const val map = "map"
    const val events = "events"
    const val search = "search"
    const val other = "other"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.dashboard,
    showOnboardingInitially: Boolean = true
) {
    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

//    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.Onboarding.start) {
            BackHandler {
                finishActivity()
            }
            OnboardingWelcomeScreen(
                onboardingComplete = {
                    onboardingComplete.value = true
                }
            )
        }
//        composable(MainDestinations.ONBOARDING_ROUTE) {
//            // Intercept back in Onboarding: make it finish the activity
//            BackHandler {
//                finishActivity()
//            }
//
//            Onboarding(
//                onboardingComplete = {
//                    // Set the flag so that onboarding is not shown next time.
//                    onboardingComplete.value = true
//                    actions.onboardingComplete()
//                }
//            )
//        }
        composable(route = Destinations.dashboard) {
            DashboardScreen()
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
    }
}