package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.placeholder.material.placeholder
import com.lambdadigamma.core.Status
import com.lambdadigamma.fuel.FuelDashboardData
import com.lambdadigamma.fuel.dashboard.DashboardFuelViewModel
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelDashboardCard(modifier: Modifier = Modifier) {

    val viewModel: DashboardFuelViewModel = hiltViewModel()

    val placeholderData = FuelDashboardData(
        type = "L DIESEL",
        location = "Aachen",
        price = 2.109,
        numberOfStations = 12
    )

    val dashboardResource by viewModel.load().observeAsState()

    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Box(Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .blur(if (dashboardResource?.status == Status.ERROR) 10.dp else 0.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Aktueller Ort")
                        Text(
                            text = dashboardResource?.data?.location ?: placeholderData.location,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.placeholder(
                                visible = dashboardResource?.shouldBeRedacted() ?: true,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
                            )
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val price = dashboardResource?.data?.price ?: placeholderData.price
                        Text(
                            text = "$price€",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.placeholder(
                                visible = dashboardResource?.shouldBeRedacted() ?: true,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
                            )
                        )
                        Text(
                            text = "PRO ${dashboardResource?.data?.type ?: placeholderData.type}".uppercase(),
//                    color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                val numberOfStations =
                    dashboardResource?.data?.numberOfStations ?: placeholderData.numberOfStations

                Text(
                    text = "$numberOfStations Tankstellen in Deiner näheren Umgebung haben geöffnet.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.placeholder(
                        visible = dashboardResource?.shouldBeRedacted() ?: true,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
                    )
                )
            }
            if (dashboardResource?.status == Status.ERROR) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Fehler beim Laden der Daten")
                }
            }
        }
    }

}

@Preview
@Composable
fun PetrolPrizeCardPreview() {
    MeinMoersTheme() {
        FuelDashboardCard()
    }
}