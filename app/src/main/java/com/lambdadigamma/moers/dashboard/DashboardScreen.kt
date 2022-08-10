package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.ui.TopBar
import com.lambdadigamma.fuel.dashboard.FuelDashboardCard
import com.lambdadigamma.moers.R
import com.lambdadigamma.parking.ui.DashboardParkingOverview
import com.lambdadigamma.rubbish.dashboard.DashboardRubbishComponent

enum class DashboardAction {
    Fuel, Rubbish, Parking
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onAction: (DashboardAction) -> Unit, onOpenSettings: () -> Unit) {

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.navigation_dashboard),
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.settings)) },
                            onClick = {
                                onOpenSettings()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.feedback_navigation)) },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.about_navigation)) },
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
//                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FuelDashboardCard {
                    onAction(DashboardAction.Fuel)
                }
                DashboardRubbishComponent {
                    onAction(DashboardAction.Rubbish)
                }
                DashboardParkingOverview {
                    onAction(DashboardAction.Parking)
                }
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                FuelDashboardCard {
//                    onAction(DashboardAction.Fuel)
//                }
//                DashboardRubbishComponent {
//                    onAction(DashboardAction.Rubbish)
//                }
//                DashboardParkingOverview()
//            }
        }
    )

}