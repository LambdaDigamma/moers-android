package com.lambdadigamma.radio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.NavigationBackButton
import com.lambdadigamma.core.ui.ResourcefulContent

@Composable
fun BroadcastOverviewScreen() {

    val viewModel: RadioBroadcastOverviewViewModel = hiltViewModel()

    ResourcefulContent(resource = viewModel.load(), onLoad = { /*TODO*/ }) { broadcasts ->
        BroadcastOverviewContent(radioBroadcasts = broadcasts)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastOverviewContent(radioBroadcasts: List<RadioBroadcastListUiState>) {

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Bürgerfunk")
                },
                navigationIcon = {
                    NavigationBackButton(onBack = {})
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Column() {
//                Text(text = "Bürgerfunk", style = MaterialTheme.typography.headlineMedium)

                    Text(
                        text = "Täglich zwischen 20.00 und 21.00 Uhr auf Radio K.W. (sonntags und an Feiertagen ab 19:04 Uhr), gibt es eine etwas andere Art von Radio - das Bürgerradio. Menschen aus dem Kreis Wesel Wesel verbringen viele Stunden ihrer Freizeit in den Produktionsstudios, um täglich eine Stunde Radio Stunde Radioprogramm über den Äther zu schicken.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = stringResource(R.string.contact_radio))
                    }

                }

                Text(
                    text = "Nächste Sendungen",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(bottom = 0.dp)
//                    .padding(bottom = 16.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                for (broadcast in radioBroadcasts) {
                    Column {
                        RadioBroadcastRow(
                            modifier = Modifier
                                .clickable { }
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            radioBroadcast = broadcast
                        )
                    }
                }
            }

        }
    }

}

@Preview
@Composable
private fun PreviewBroadcastOverviewScreen() {
    MeinMoersTheme {
        BroadcastOverviewScreen()
    }
}