package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lambdadigamma.moers.onboarding.*
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingNavigationGraph(
    navController: NavHostController = rememberNavController(),
    finishActivity: () -> Unit,
    onFinishOnboarding: () -> Unit,
) {

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    Scaffold(topBar = { OnboardingTop(navController) }) {
        NavHost(navController = navController, Destinations.Onboarding.graph) {
            navigation(
                Destinations.Onboarding.welcome,
                Destinations.Onboarding.graph
            ) {
                composable(Destinations.Onboarding.welcome) { backStackEntry: NavBackStackEntry ->
                    BackHandler {
                        finishActivity()
                    }
                    OnboardingWelcomeScreen(onNext = {
                        navController.navigate(Destinations.Onboarding.about)
                    })
                }
                composable(Destinations.Onboarding.about) {
                    BackHandler(onBack = {
                        navController.navigateUp()
                    })
                    OnboardingAboutScreen(viewModel = onboardingViewModel, onContinue = {
                        navController.navigate(Destinations.Onboarding.userTypeSelection)
                    })
                }
                composable(Destinations.Onboarding.userTypeSelection) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingUserTypeScreen(onContinue = {
                        navController.navigate(Destinations.Onboarding.location)
                    })
                }
                composable(Destinations.Onboarding.location) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingLocationScreen(
                        onContinue = {
                            navController.navigate(Destinations.Onboarding.rubbishStreet)
                        }
                    )
                }
                composable(Destinations.Onboarding.rubbishStreet) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingRubbishStreetScreen(
                        onContinue = {
                            navController.navigate(Destinations.Onboarding.petrol)
                        }
                    )
                }
                composable(Destinations.Onboarding.petrol) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingPetrolScreen(onContinue = {
                        navController.navigate(Destinations.Onboarding.done)
                    })
                }
                composable(Destinations.Onboarding.done) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingDoneScreen(onContinue = {
                        onboardingViewModel.setFinished()
                        onFinishOnboarding()
                    })
                }
            }
        }
    }
}

@Composable
fun OnboardingTop(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Destinations.Onboarding.welcome

    if (currentRoute != Destinations.Onboarding.welcome) {

        val onboardingStep = OnboardingStep.fromRoute(currentRoute)
        val state = onboardingStep?.toUiState()

        state?.let {
            OnboardingTopBar(it)
        }

    }
}