package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.ui.TopBar

@Composable
fun DashboardScreen() {

    Column() {
        TopBar(title = stringResource(id = R.string.navigation_dashboard))
    }

}