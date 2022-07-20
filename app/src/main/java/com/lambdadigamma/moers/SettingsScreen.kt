package com.lambdadigamma.moers

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jamal.composeprefs3.ui.GroupHeader
import com.jamal.composeprefs3.ui.PrefsScreen
import com.jamal.composeprefs3.ui.prefs.DropDownPref
import com.jamal.composeprefs3.ui.prefs.SwitchPref
import com.jamal.composeprefs3.ui.prefs.TextPref
import com.lambdadigamma.core.theme.MeinMoersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {

//    rememberSystemUiController().setNavigationBarColor(
//        MaterialTheme.colorScheme.background,
//        navigationBarContrastEnforced = true
//    )

    Scaffold(topBar = {
        LargeTopAppBar(
            title = { Text(text = stringResource(id = R.string.settings)) },
            navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors()
//            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        )
    }) {
        PrefsScreen(
            dataStore = LocalContext.current.dataStore,
            modifier = Modifier.padding(it)
        ) {

            prefsGroup(
                header = { GroupHeader(title = stringResource(R.string.settings_user_header)) }
            ) {
                prefsItem {
                    DropDownPref(
                        key = "user_type",
                        title = stringResource(R.string.settings_user_type),
                        useSelectedAsSummary = true,
                        entries = mapOf(
                            "0" to stringResource(R.string.settings_user_type_citizen),
                            "1" to stringResource(R.string.settings_user_type_guest),
                        )
                    )
                }
            }

            prefsGroup(
                header = { GroupHeader(title = stringResource(R.string.settings_fuel_header)) }
            ) {
                prefsItem {
                    DropDownPref(
                        key = "fuel_type",
                        title = stringResource(R.string.settings_petrol_type_label),
                        useSelectedAsSummary = true,
                        entries = mapOf(
                            "0" to "Diesel",
                            "1" to "E5",
                            "2" to "E10",
                        )
                    )
                }
            }

            prefsGroup(
                header = { GroupHeader(title = stringResource(R.string.settings_rubbish_header)) }
            ) {
                prefsItem {
                    SwitchPref(
                        key = "rubbish_schedule_active",
                        title = stringResource(R.string.settings_rubbish_schedule_active),
                    )
                    TextPref(
                        title = stringResource(R.string.settings_rubbish_street),
                        summary = "Adlerstraße",
                        onClick = {}
                    )
                    TextPref(
                        title = stringResource(R.string.settings_rubbish_reminder_time),
                        summary = "20:00 Uhr am Vortag",
                        onClick = {}
                    )
                }
            }

            /*
            - [ ] Benutzer
                - [ ] Art
            - [ ] Tanken
                - [ ] Kraftstoff
            - [ ] Abfuhrkalender
                - [ ] Aktiviert
                - [ ] Straße
                - [ ] Erinnerung
             */

        }
    }

}

@Preview
@Composable
fun SettingsScreenPreviews() {

    MeinMoersTheme {
        SettingsScreen(onBack = {})
    }

}
