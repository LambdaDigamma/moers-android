package com.lambdadigamma.moers.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.ui.TopBar

@Composable
fun ExploreScreen() {
    Column {
        TopBar(title = stringResource(id = R.string.navigation_explore))
    }
}