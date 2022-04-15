package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lambdadigamma.moers.dashboard.DashboardScreen
import com.lambdadigamma.moers.events.ui.EventsScreen
import com.lambdadigamma.moers.explore.ExploreScreen
import com.lambdadigamma.moers.onboarding.*
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBar
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBarUiState
import com.lambdadigamma.moers.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingNavigationGraph(
    navController: NavHostController = rememberNavController(),
    finishActivity: () -> Unit
) {

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val onboardingState = onboardingViewModel.currentOnboardingState
        .collectAsState(
            OnboardingTopBarUiState.default()
        )

    Scaffold(topBar = { OnboardingTop(navController, onboardingState.value) }) {
        NavHost(navController = navController, Destinations.Onboarding.welcome) {
            navigation(Destinations.Onboarding.welcome, Destinations.Onboarding.graph) {
                composable(Destinations.Onboarding.welcome) { backStackEntry: NavBackStackEntry ->
                    BackHandler {
                        finishActivity()
                    }
                    OnboardingWelcomeScreen(onNext = {
                        navController.navigate(Destinations.Onboarding.about)
                    })
                }
                composable(Destinations.Onboarding.about) {
                    OnboardingAboutScreen(viewModel = onboardingViewModel, onContinue = {
                        onboardingViewModel.iterateNextStep()
                        navController.navigate(Destinations.Onboarding.userTypeSelection)
                    })
                }
                composable(Destinations.Onboarding.userTypeSelection) {
                    OnboardingUserTypeScreen(onContinue = {
                        onboardingViewModel.iterateNextStep()
                        navController.navigate(Destinations.Onboarding.location)
                    })
                }
                composable(Destinations.Onboarding.location) {
                    OnboardingLocationScreen(onContinue = {
                        onboardingViewModel.iterateNextStep()
                        navController.navigate(Destinations.Onboarding.petrol)
                    })
                }
                composable(Destinations.Onboarding.petrol) {
                    OnboardingPetrolScreen(onContinue = {
                        onboardingViewModel.iterateNextStep()
                        navController.navigate(Destinations.Onboarding.done)
                    })
                }
                composable(Destinations.Onboarding.done) {
                    OnboardingDoneScreen(onContinue = {})
                }
            }
            main()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.main() {
    navigation(Destinations.dashboard, Destinations.graph) {
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

@Composable
fun OnboardingTop(navController: NavController, uiState: OnboardingTopBarUiState) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Destinations.Onboarding.welcome

    if (currentRoute != Destinations.Onboarding.welcome) {
        OnboardingTopBar(uiState)
    }

}