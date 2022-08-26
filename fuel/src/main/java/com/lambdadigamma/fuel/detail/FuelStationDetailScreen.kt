package com.lambdadigamma.fuel.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.ui.ResourcefulContent
import com.lambdadigamma.fuel.list.FuelStationDetailViewModel

@Composable
fun FuelStationDetailScreen(id: String, onBack: () -> Unit) {

    val viewModel = hiltViewModel<FuelStationDetailViewModel>()
    viewModel.id = id

    val data = viewModel.load()

    ResourcefulContent(resource = data, onLoad = { /*TODO*/ }) {
        FuelStationDetailContent(
            fuelStation = it,
            onBack = onBack
        )
    }
}
