package com.lambdadigamma.moers.fuel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.Status
import com.lambdadigamma.fuel.FuelType
import com.lambdadigamma.moers.rubbish.RubbishScheduleLoadingScreen
import com.lambdadigamma.moers.ui.TopBar

@Composable
fun FuelStationListScreen() {

    val viewModel: FuelStationListViewModel = hiltViewModel()
    val stations by viewModel.load().observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "Kraftstoff",
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
                        modifier = Modifier.weight(1f)
                    )
                    FuelStationSearchInfo(type = FuelType.DIESEL)
                }
            }
            Status.LOADING -> {
                RubbishScheduleLoadingScreen()
            }
            Status.ERROR -> {
//                NewsErrorScreen(error = news?.errorMessage ?: "")
            }
            else -> {

            }
        }
    }
}