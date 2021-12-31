package com.lambdadigamma.moers.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.lambdadigamma.moers.NavGraph
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@Composable
fun App(finishActivity: () -> Unit) {
    MeinMoersTheme {
        val tabs = remember { AppTab.values() }
        val navController = rememberNavController()
        Scaffold(bottomBar = { BottomBar(navController = navController, tabs) }) {
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
            )
        }
    }
}