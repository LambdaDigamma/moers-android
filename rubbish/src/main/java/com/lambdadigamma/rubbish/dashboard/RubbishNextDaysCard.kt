package com.lambdadigamma.rubbish.dashboard

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.DateUtils
import com.lambdadigamma.core.Status
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RubbishNextDays(modifier: Modifier = Modifier, onClick: () -> Unit) {

    val viewModel: DashboardRubbishViewModel = hiltViewModel()
    val nextDays by viewModel.load().observeAsState()

    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 140.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when (nextDays?.status) {
                Status.SUCCESS -> {
                    val data = nextDays?.data.orEmpty()

                    for (dayGroup in data) {
                        val dateTitle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DateUtils.format(dayGroup.first().parsedDate, FormatStyle.SHORT)
                        } else {
                            ""
                        }
                        RubbishDayCard(
                            title = dateTitle,
                            items = dayGroup,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                else -> {

                }
//                Status.LOADING -> {
//
//                }
//                Status.ERROR -> {
//
//                }
            }

//            for (dayGroup in nextDays)
//            RubbishDayCard(
//                title = "Morgen", arrayOf(
//                    RubbishPickupItem(Date(), RubbishWasteType.RESIDUAL),
//                    RubbishPickupItem(Date(), RubbishWasteType.PAPER)
//                ),
//                modifier = Modifier.weight(1f)
//            )
//            RubbishDayCard(
//                title = "24.12.", arrayOf(
//                    RubbishPickupItem(Date(), RubbishWasteType.ORGANIC)
//                ),
//                modifier = Modifier.weight(1f)
//            )
//            RubbishDayCard(
//                title = "25.12.", arrayOf(
//                    RubbishPickupItem(Date(), RubbishWasteType.PLASTIC)
//                ),
//                modifier = Modifier.weight(1f)
//            )
        }
    }
}
