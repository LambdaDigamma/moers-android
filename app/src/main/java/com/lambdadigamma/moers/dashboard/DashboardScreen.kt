package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.ui.TopBar
import com.lambdadigamma.fuel.dashboard.FuelDashboardCard
import com.lambdadigamma.moers.R
import com.lambdadigamma.rubbish.dashboard.RubbishNextDays

enum class DashboardAction {
    Fuel, Rubbish
}

@Composable
fun DashboardScreen(onAction: (DashboardAction) -> Unit, onOpenSettings: () -> Unit) {

    val context = LocalContext.current

//    val rubbishRepository = RubbishRepository(
//        context = context,
//        remoteDataSource = RubbishRemoteDataSource(
//            rubbishApi = DefaultRubbishApiService.getRubbishService(),
//            ioDispatcher = Dispatchers.IO
//        )
//    )

    val scope = rememberCoroutineScope()

//    val remindersEnabled = rubbishRepository.reminderEnabled.collectAsState(initial = false)

//    context.rubbishSettingsDataStore.data.map { it.remindersEnabled }
//        .collectAsState(initial = false)

    var showMenu by remember { mutableStateOf(false) }

    Column() {
        TopBar(
            title = stringResource(id = R.string.navigation_dashboard),
            actions = {
//                IconButton(onClick = onOpenSettings) {
//                    Icon(
//                        Icons.Default.Settings,
//                        contentDescription = stringResource(id = R.string.settings)
//                    )
//                }
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

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FuelDashboardCard(modifier = Modifier.clickable {
                onAction(DashboardAction.Fuel)
            })
            RubbishNextDays(modifier = Modifier.clickable {
                onAction(DashboardAction.Rubbish)
            })

//            if (remindersEnabled.value) {
//                Text("Enabled")
//            } else {
//                Text("not enabled")
//            }

//            Button(onClick = {
//                scope.launch {
//                    if (remindersEnabled.value) {
//                        rubbishRepository.disableReminders()
//                    } else {
//                        rubbishRepository.enableReminders()
//                    }
//                }
//            }) {
//                if (remindersEnabled.value) {
//                    Text("disable reminders")
//                } else {
//                    Text("enable reminders")
//                }
//            }

        }

    }

}