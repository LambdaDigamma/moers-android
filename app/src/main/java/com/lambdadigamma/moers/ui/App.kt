package com.lambdadigamma.moers.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.lambdadigamma.moers.OnboardingNavigationGraph
import com.lambdadigamma.moers.ui.theme.LegacyMeinMoersTheme
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@ExperimentalMaterial3Api
@Composable
fun App(finishActivity: () -> Unit) {
    LegacyMeinMoersTheme {
        MeinMoersTheme {
            val tabs = remember { AppTab.values() }
            val navController = rememberNavController()

            OnboardingNavigationGraph(navController, finishActivity)

//            Scaffold(bottomBar = { BottomBar(navController = navController, tabs) }) {
//                NavGraph(
//                    finishActivity = finishActivity,
//                    navController = navController,
//                    startDestination = Destinations.Onboarding.graph
//                )
//            }
        }
    }
}