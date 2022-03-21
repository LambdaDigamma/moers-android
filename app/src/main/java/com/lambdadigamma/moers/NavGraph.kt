package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Onboarding.graph
) {
    val actions = remember(navController) { MainActions(navController) }
    val onboardingActions = remember(navController) { OnboardingActions(navController) }
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

//    val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
//    val onboardingComplete = onboardingViewModel.isOnboardingComplete
//
//        .collectAsState(initial = true)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // --- Onboarding ---

        navigation(
            Destinations.Onboarding.welcome,
            Destinations.Onboarding.graph
        ) {
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
                OnboardingAboutScreen(
                    viewModel = onboardingViewModel,
                    onContinue = {
                        onboardingActions.continueToUserTypeSelection(backStackEntry)
                    }
                )
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

        // ------------------------------------------------------------

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
