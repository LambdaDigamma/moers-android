package com.lambdadigamma.fuel.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.geo.GoogleMapsNavigationProvider
import com.lambdadigamma.core.geo.Point
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.AddressContainer
import com.lambdadigamma.core.ui.NavigationBackButton
import com.lambdadigamma.core.ui.OpeningHoursContainer
import com.lambdadigamma.core.ui.OpeningHoursEntry
import com.lambdadigamma.fuel.R
import com.lambdadigamma.fuel.list.FuelStationUiState
import com.lambdadigamma.fuel.ui.FuelPriceText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationDetailContent(fuelStation: FuelStationUiState, onBack: () -> Unit) {

    Scaffold(topBar = {
        SmallTopAppBar(
            title = { Text(text = stringResource(R.string.parking_area_detail_title)) },
            navigationIcon = {
                NavigationBackButton(onBack = onBack)
            }
        )
    }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
        ) {

//            Map(modifier = Modifier
//                .fillMaxSize()
//                .height(400.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = fuelStation.brand,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(text = fuelStation.name, color = MaterialTheme.colorScheme.secondary)
                    fuelStation.distance?.let { distance ->
                        Text(
                            text = " • ${distance}km",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

//                Divider(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
//                )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                fuelStation.priceDiesel?.let { priceDiesel ->
                    PricePanel(
                        price = priceDiesel,
                        label = stringResource(R.string.fuel_label_diesel),
                        modifier = Modifier.weight(1f)
                    )
                }
                fuelStation.priceE5?.let { priceE5 ->
                    PricePanel(
                        price = priceE5,
                        label = stringResource(R.string.fuel_label_e5),
                        modifier = Modifier.weight(1f)
                    )

                }
                fuelStation.priceE10?.let { priceE10 ->
                    PricePanel(
                        price = priceE10,
                        label = stringResource(R.string.fuel_label_e10),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

//            Divider(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
//            )

            OpeningHoursContainer(
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

            fuelStation.address?.let {
                AddressContainer(
                    address = it,
                    modifier = Modifier.padding(16.dp)
                )
            }

            val context = LocalContext.current

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    GoogleMapsNavigationProvider(context).startNavigation(fuelStation.point)
                }
            ) {
                Text(text = "Navigation starten")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PricePanel(price: Double, label: String, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label)
            FuelPriceText(
                price = price,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Preview
@Composable
fun FuelStationDetailScreenPreview() {
    MeinMoersTheme {
        FuelStationDetailContent(
            fuelStation = FuelStationUiState(
                id = "5",
                brand = "Kuster Energy",
                name = "Kuster Energy",
                point = Point(latitude = 52.5, longitude = 13.4),
                price = 1.659,
                distance = 1.2,
            ),
            onBack = {}
        )
    }
}