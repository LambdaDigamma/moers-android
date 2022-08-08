package com.lambdadigamma.fuel.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.ui.*
import com.lambdadigamma.fuel.list.FuelStationDetailViewModel
import com.lambdadigamma.fuel.list.FuelStationUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationDetailScreen(id: String) {

    val viewModel = hiltViewModel<FuelStationDetailViewModel>()
    viewModel.id = id

    val data = viewModel.load()

    ResourcefulContent(resource = data, onLoad = { /*TODO*/ }) {
//        repeat(30) {
//            Text(text = "Item $it")
//        }
        Column(Modifier.verticalScroll(rememberScrollState())) {
            FuelStationDetail(
                data = it
            )
        }
    }
}

@Composable
fun FuelStationDetail(data: FuelStationUiState) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        TopBar(title = "Details")

        Column(
            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
                .scrollable(scrollState, orientation = Orientation.Vertical)
        ) {

//            Map(modifier = Modifier
//                .fillMaxSize()
//                .height(400.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = data.brand,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(text = data.name, color = MaterialTheme.colorScheme.secondary)
                    data.distance?.let { distance ->
                        Text(text = " • ${distance}km", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                PricePanel(
                    price = 1.98,
                    label = "Diesel",
                    modifier = Modifier.weight(1f)
                )
                PricePanel(
                    price = 2.04,
                    label = "Super E5",
                    modifier = Modifier.weight(1f)
                )
                PricePanel(
                    price = 1.92,
                    label = "Super E10",
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
            )

            OpeningHoursContainer(
                label = "Öffnungszeiten",
                entries = listOf(
                    OpeningHoursEntry(label = "Mo-Fr", value = "06:00 - 22:30"),
                    OpeningHoursEntry(label = "Samstag", value = "07:00 - 22:00"),
                    OpeningHoursEntry(label = "Sonntag", value = "08:00 - 22:00")
                ),
                modifier = Modifier.padding(16.dp)
            )

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
            )

            AddressContainer(label = "Adresse", modifier = Modifier.padding(16.dp))

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Navigation starten")
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PricePanel(price: Double, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label)
            Text(
                text = "$price€",
                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Preview
@Composable
fun FuelStationDetailScreenPreview() {
    FuelStationDetail(
        data = FuelStationUiState(
            id = "5",
            brand = "Kuster Energy",
            name = "Kuster Energy",
            price = 1.659,
            distance = 1.2,
        ),
    )
}