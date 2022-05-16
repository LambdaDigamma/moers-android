package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.ui.TopBar
import com.lambdadigamma.rubbish.RubbishRepository
import com.lambdadigamma.rubbish.source.DefaultRubbishApiService
import com.lambdadigamma.rubbish.source.RubbishRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(onOpenSettings: () -> Unit) {

    val context = LocalContext.current

    val rubbishRepository = com.lambdadigamma.rubbish.RubbishRepository(
        context = context,
        remoteDataSource = com.lambdadigamma.rubbish.source.RubbishRemoteDataSource(
            rubbishApi = com.lambdadigamma.rubbish.source.DefaultRubbishApiService.getRubbishService(),
            ioDispatcher = Dispatchers.IO
        )
    )

    val scope = rememberCoroutineScope()

    val remindersEnabled = rubbishRepository.reminderEnabled.collectAsState(initial = false)

//    context.rubbishSettingsDataStore.data.map { it.remindersEnabled }
//        .collectAsState(initial = false)

    Column() {
        TopBar(
            title = stringResource(id = R.string.navigation_dashboard),
            actions = {
                IconButton(onClick = onOpenSettings) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
            }
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RubbishNextDays()
            PetrolPrizeCard()

            if (remindersEnabled.value) {
                Text("Enabled")
            } else {
                Text("not enabled")
            }

            Button(onClick = {
                scope.launch {
                    if (remindersEnabled.value) {
                        rubbishRepository.disableReminders()
                    } else {
                        rubbishRepository.enableReminders()
                    }
                }
            }) {
                if (remindersEnabled.value) {
                    Text("disable reminders")
                } else {
                    Text("enable reminders")
                }
            }

        }

    }

}