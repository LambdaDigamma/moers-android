package com.lambdadigamma.moers.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lambdadigamma.core.ui.TopBar
import com.lambdadigamma.moers.R

@Composable
fun ExploreScreen() {
    Column {
        TopBar(title = stringResource(id = R.string.navigation_explore))
    }
}