package com.lambdadigamma.fuel.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.ui.ResourcefulContent
import com.lambdadigamma.fuel.list.FuelStationDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationDetailScreen(id: String, onBack: () -> Unit) {

    val viewModel = hiltViewModel<FuelStationDetailViewModel>()
    viewModel.id = id

    val data = viewModel.load()

    ResourcefulContent(resource = data, onLoad = { /*TODO*/ }) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            FuelStationDetailContent(
                fuelStation = it,
                onBack = onBack
            )
        }
    }
}
