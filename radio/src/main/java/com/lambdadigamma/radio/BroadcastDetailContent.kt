package com.lambdadigamma.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.NavigationBackButton

data class BroadcastDetailUiState(
    val title: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastDetailContent(broadcast: BroadcastDetailUiState) {

    Scaffold(topBar = {
        SmallTopAppBar(title = {
            Text(text = "Details")
        }, navigationIcon = {
            NavigationBackButton(onBack = {
                
            })
        })
    }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Column {
                Text(
                    text = broadcast.title,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "31.08.22, 20:04–20:56",
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Antworten auf diese Frage gibt's im Magazin aus Neukirchen-Vluyn und dazu viel Oldie-Musik.",
                )

            }


        }
    }

}

@Preview
@Composable
private fun PreviewBroadcastDetailContent() {

    val broadcast = BroadcastDetailUiState(
        title = "Was ist los im Kreis Wesel"
    )

    MeinMoersTheme {
        BroadcastDetailContent(broadcast = broadcast)
    }
}