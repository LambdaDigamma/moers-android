package com.lambdadigamma.moers.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.ui.TopBar

@Composable
fun SearchScreen() {
    Column {
        TopBar(title = stringResource(id = R.string.navigation_search))
    }
}