package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetrolPrizeCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Aktueller Ort")
                    Text(
                        text = "Moers",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "2.00€",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "PRO L DIESEL",
//                    color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Text(
                text = "27 Tankstellen in Deiner näheren Umgebung haben geöffnet.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

}

@Preview
@Composable
fun PetrolPrizeCardPreview() {
    MeinMoersTheme(darkTheme = true) {
        PetrolPrizeCard()
    }
}