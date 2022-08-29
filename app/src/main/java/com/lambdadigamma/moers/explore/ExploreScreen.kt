package com.lambdadigamma.moers.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.TopBar
import com.lambdadigamma.moers.R

enum class ExploreNavigationAction {
    RadioBroadcasts
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(onNavigate: (ExploreNavigationAction) -> Unit) {

    Scaffold(topBar = {
        TopBar(title = stringResource(id = R.string.navigation_explore))
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                onNavigate(ExploreNavigationAction.RadioBroadcasts)
            }) {
                Text(text = "Bürgerfunk")
            }

        }
    }

}

@Preview
@Composable
fun ExploreScreenPreview() {
    MeinMoersTheme {
        ExploreScreen(onNavigate = {
            
        })
    }
}