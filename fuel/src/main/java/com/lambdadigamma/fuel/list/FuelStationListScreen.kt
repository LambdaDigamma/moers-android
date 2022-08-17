package com.lambdadigamma.fuel.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.Status
import com.lambdadigamma.core.ui.NavigationBackButton
import com.lambdadigamma.fuel.R
import com.lambdadigamma.fuel.R.drawable
import com.lambdadigamma.fuel.data.FuelType

@Composable
fun FuelStationListScreen(onBack: () -> Unit, onShowFuelStation: (String) -> Unit) {

    val viewModel: FuelStationListViewModel = hiltViewModel()
    val stations by viewModel.load().observeAsState()
    val fuelType by viewModel.fuelType.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SmallTopAppBar(
            title = {
                Text(stringResource(R.string.fuel_stations_title))
            },
            navigationIcon = {
                NavigationBackButton(onBack = onBack)
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Info, "")
                }
            }
        )
        when (stations?.status) {
            Status.SUCCESS -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    FuelStationList(
                        items = stations?.data.orEmpty(),
                        modifier = Modifier.weight(1f),
                        onShowFuelStation = onShowFuelStation
                    )
                    FuelStationSearchInfo(type = fuelType ?: FuelType.DIESEL)
                }
            }
            Status.LOADING -> {
//                RubbishScheduleLoadingScreen()
            }
            Status.ERROR -> {
                FuelStationsErrorScreen(stations?.errorMessage ?: "Error")
            }
            else -> {

            }
        }
    }
}

@Composable
fun FuelStationsErrorScreen(error: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_outline_error_outline_24),
                contentDescription = "Die Tankstellen können im Moment nicht geladen werden.",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.surfaceTint
            )
            Text(
                text = error,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.surfaceTint
            )
//            TextButton(onClick = { /*TODO*/ }) {
//                Text(text = "Neu laden")
//            }
        }
    }
}