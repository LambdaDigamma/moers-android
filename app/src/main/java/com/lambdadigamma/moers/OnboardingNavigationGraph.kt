package com.lambdadigamma.moers

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lambdadigamma.moers.onboarding.*
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBar

fun positionForRoute(route: String): Int {

    val routes = arrayOf<String>(
        Destinations.Onboarding.welcome,
        Destinations.Onboarding.about,
        Destinations.Onboarding.userTypeSelection,
        Destinations.Onboarding.location,
        Destinations.Onboarding.rubbishStreet,
        Destinations.Onboarding.petrol,
        Destinations.Onboarding.done
    )

    return routes.indexOf(route)
}

val tweenTimeEnter = 500

@OptIn(ExperimentalAnimationApi::class)
private fun enterTransition(
    initialRoute: String,
    destinationRoute: String,
    contentScope: AnimatedContentScope<NavBackStackEntry>
): EnterTransition {
    val initialPosition = positionForRoute(initialRoute)
    val destinationPosition = positionForRoute(destinationRoute)

    return if (destinationPosition > initialPosition) {
        contentScope.slideIntoContainer(
            AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(tweenTimeEnter)
        )
    } else {
        contentScope.slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right,
            animationSpec = tween(tweenTimeEnter)
        )
    }

//        tween(
//            start = start.toFloat(),
//            end = end.toFloat(),
//            easing = tween.easeInOut,
//            onEnd = {
//                onboardingViewModel.onEnterTransitionEnd()
//            }
//        )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun OnboardingNavigationGraph(
    navController: NavHostController = rememberAnimatedNavController(),
    finishActivity: () -> Unit,
    onFinishOnboarding: () -> Unit,
) {

    rememberSystemUiController().setStatusBarColor(
        MaterialTheme.colorScheme.background, darkIcons = !isSystemInDarkTheme()
    )

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    Scaffold(topBar = { OnboardingTop(navController) }, modifier = Modifier.systemBarsPadding()) {
        AnimatedNavHost(
            navController = navController,
            Destinations.Onboarding.graph,
            modifier = Modifier.padding(it)
        ) {
            navigation(
                Destinations.Onboarding.welcome,
                Destinations.Onboarding.graph
            ) {
                composable(
                    route = Destinations.Onboarding.welcome,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.welcome,
                                contentScope = this
                            )
                        }
                    },
                ) { backStackEntry: NavBackStackEntry ->
                    BackHandler {
                        finishActivity()
                    }
                    OnboardingWelcomeScreen(onNext = {
                        navController.navigate(Destinations.Onboarding.about)
                    })
                }
                composable(
                    route = Destinations.Onboarding.about,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.about,
                                contentScope = this
                            )
                        }
                    },
//                    exitTransition = {
//                        slideOutOfContainer(
//                            AnimatedContentScope.SlideDirection.Right,
//                            animationSpec = tween(tweenTimeEnter)
//                        )
//                    }
                ) {
                    BackHandler(onBack = {
                        navController.navigateUp()
                    })
                    OnboardingAboutScreen(viewModel = onboardingViewModel, onContinue = {
                        navController.navigate(Destinations.Onboarding.userTypeSelection)
                    })
                }
                composable(
                    route = Destinations.Onboarding.userTypeSelection,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.userTypeSelection,
                                contentScope = this
                            )
                        }
                    },
                ) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingUserTypeScreen(onContinue = {
                        navController.navigate(Destinations.Onboarding.location)
                    })
                }
                composable(
                    route = Destinations.Onboarding.location,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.location,
                                contentScope = this
                            )
                        }
                    },
                ) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingLocationScreen(
                        onContinue = {
                            navController.navigate(Destinations.Onboarding.rubbishStreet)
                        }
                    )
                }
                composable(
                    route = Destinations.Onboarding.rubbishStreet,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.rubbishStreet,
                                contentScope = this
                            )
                        }
                    },
                ) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingRubbishStreetScreen(
                        onContinue = {
                            navController.navigate(Destinations.Onboarding.petrol)
                        }
                    )
                }
                composable(
                    route = Destinations.Onboarding.petrol,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.petrol,
                                contentScope = this
                            )
                        }
                    },
                ) {
                    BackHandler {
                        navController.navigateUp()
                    }
                    OnboardingPetrolScreen(onContinue = {
                        navController.navigate(Destinations.Onboarding.done)
                    })
                }
                composable(
                    route = Destinations.Onboarding.done,
                    enterTransition = {
                        initialState.destination.route?.let { it1 ->
                            enterTransition(
                                initialRoute = it1,
                                destinationRoute = Destinations.Onboarding.done,
                                contentScope = this
                            )
                        }
                    },
                ) {
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
            Column(modifier = Modifier.fillMaxWidth()) {
                OnboardingTopBar(it)
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            }
        }

    }
}