package com.lambdadigamma.moers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.Status
import com.lambdadigamma.moers.ui.TopBar
import com.lambdadigamma.rubbish.RubbishScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RubbishListScreen() {

    val viewModel: RubbishScheduleViewModel = hiltViewModel()
    val rubbishSchedule by viewModel.rubbishSchedule.observeAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = "load_rubbish_schedule") {
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .scrollable(rememberScrollState(), orientation = Orientation.Horizontal)
    ) {
        TopBar(
            title = "Abfallkalender",
        )

        when (rubbishSchedule?.status) {
            Status.SUCCESS -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    val data = rubbishSchedule?.data.orEmpty()

                    items(items = data) { item ->

                        Card(onClick = {
//                            item.id?.let(onShowRssItem)
                        }) {
                            Column() {
                                Text(item.type.value)
//                                AsyncImage(
//                                    model = item.image,
//                                    contentDescription = null,
//                                    alignment = Alignment.TopCenter,
//                                    contentScale = ContentScale.FillWidth,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .aspectRatio(ratio = (16.0f / 9.0f))
//                                )

//                                Column(
//                                    modifier = Modifier.padding(12.dp),
//                                    verticalArrangement = Arrangement.spacedBy(4.dp)
//                                ) {
//                                    Text(
//                                        text = item.title,
//                                        fontWeight = FontWeight.SemiBold,
//                                        style = MaterialTheme.typography.bodyMedium.copy(
//                                            lineHeight = 18.sp,
//                                        )
//                                    )
//                                    Text(
//                                        text = item.publishDate ?: "",
//                                        style = MaterialTheme.typography.bodyMedium
//                                    )
//                                }
                            }
                        }
                    }
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

@Composable
fun RubbishScheduleLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Lädt Abfallkalender…")
        }
    }

}