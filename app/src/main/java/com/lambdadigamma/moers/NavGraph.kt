package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lambdadigamma.moers.dashboard.DashboardScreen
import com.lambdadigamma.moers.events.ui.EventsScreen
import com.lambdadigamma.moers.explore.ExploreScreen
import com.lambdadigamma.moers.onboarding.*
import com.lambdadigamma.moers.search.SearchScreen

@Composable
fun NavGraph(
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Onboarding.graph,
    showOnboardingInitially: Boolean = true
) {
    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

    val actions = remember(navController) { MainActions(navController) }
    val onboardingActions = remember(navController) { OnboardingActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(Destinations.Onboarding.welcome, Destinations.Onboarding.graph) {
            composable(Destinations.Onboarding.welcome) { backStackEntry: NavBackStackEntry ->
                BackHandler {
                    // Intercept back in Onboarding: make it finish the activity
                    finishActivity()
                }
                OnboardingWelcomeScreen(
                    onNext = {
                        onboardingActions.continueToPrivacy(backStackEntry)
                    }
                )
//                onboardingComplete = {
//                    onboardingComplete.value = true
//                    actions.onboardingComplete()
//                }
            }
            composable(Destinations.Onboarding.about) { backStackEntry: NavBackStackEntry ->
                OnboardingAboutScreen(onContinue = {
                    onboardingActions.continueToUserTypeSelection(backStackEntry)
                })
            }
            composable(Destinations.Onboarding.userTypeSelection) { backStackEntry: NavBackStackEntry ->
                OnboardingUserTypeScreen(onContinue = {
                    onboardingActions.continueToLocation(backStackEntry)
                })
            }
            composable(Destinations.Onboarding.location) {
                OnboardingLocationScreen(onContinue = {
                    onboardingActions.continueToPetrol(it)
                })
            }
            composable(Destinations.Onboarding.notifications) {
                OnboardingNotificationScreen()
            }
            composable(Destinations.Onboarding.petrol) {
                OnboardingPetrolScreen()
            }
        }
        composable(route = Destinations.dashboard) {
            DashboardScreen(onOpenSettings = {

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
    }
}