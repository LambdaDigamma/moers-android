package com.lambdadigamma.moers.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.data.rubbish.*
import java.util.*

@Composable
fun RubbishNextDays() {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 140.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DayCard(
                title = "Morgen", arrayOf(
                    RubbishPickupItem(Date(), RubbishWasteType.RESIDUAL),
                    RubbishPickupItem(Date(), RubbishWasteType.PAPER)
                ),
                modifier = Modifier.weight(1f)
            )
            DayCard(
                title = "24.12.", arrayOf(
                    RubbishPickupItem(Date(), RubbishWasteType.ORGANIC)
                ),
                modifier = Modifier.weight(1f)
            )
            DayCard(
                title = "25.12.", arrayOf(
                    RubbishPickupItem(Date(), RubbishWasteType.PLASTIC)
                ),
                modifier = Modifier.weight(1f)
            )
        }

    }

}

@Composable
private fun DayCard(title: String, items: Array<RubbishPickupItem>, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        Divider()
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach {
                CollectionChip(it)
            }
        }
    }

}

@Composable
private fun CollectionChip(pickupItem: RubbishPickupItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(pickupItem.type.backgroundColor())
            .drawBehind {
                val strokeWidth = 4 * density
                val y = size.height

                drawLine(
                    pickupItem.type.color(),
                    Offset(0f, 0f),
                    Offset(0f, y),
                    strokeWidth
                )
            }
    ) {

        Text(
            pickupItem.type.shortName(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .padding(start = 4.dp)
        )
    }


}
