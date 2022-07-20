package com.lambdadigamma.moers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.Status
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.TopBar
import com.lambdadigamma.rubbish.RubbishScheduleViewModel
import com.lambdadigamma.rubbish.ui.RubbishScheduleList
import com.lambdadigamma.rubbish.ui.RubbishScheduleLoadingScreen

@Composable
fun RubbishListScreen() {

    val viewModel: RubbishScheduleViewModel = hiltViewModel()
    val rubbishSchedule by viewModel.rubbishSchedule.observeAsState()
    val rubbishCollectionStreet by viewModel.rubbishCollectionStreet.observeAsState()

    LaunchedEffect(key1 = "load_rubbish_schedule") {
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "Abfallkalender",
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Info, "")
                }
            }
        )

        when (rubbishSchedule?.status) {
            Status.SUCCESS -> {
                RubbishScheduleList(items = rubbishSchedule?.data.orEmpty())
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

@Composable
fun RubbishStreetInfo() {

    Row {

    }

}

@Preview
@Composable
fun RubbishListScreen_Preview() {
    MeinMoersTheme {
        RubbishListScreen()
    }
}