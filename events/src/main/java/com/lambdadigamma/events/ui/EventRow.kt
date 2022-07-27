package com.lambdadigamma.events.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.theme.MeinMoersTheme

@Composable
fun EventRow(event: EventUi, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column() {
            Text(text = event.name, fontWeight = FontWeight.Medium)
            Text(text = event.date, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun EventRowPreview() {
    MeinMoersTheme() {
        EventRow(
            event = EventUi(
                id = 1,
                name = "Cycling with the mayor",
                description = "Event Description",
                date = "Mo, 19th of May 2020",
            ), modifier = Modifier.padding(16.dp)
        )
    }
}