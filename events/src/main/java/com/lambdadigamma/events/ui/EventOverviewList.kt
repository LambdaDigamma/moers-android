package com.lambdadigamma.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.events.models.Event

data class EventUi(
    val id: Int,
    val name: String,
    val description: String?,
    val date: String,
    val isLive: Boolean = false,
) {

    constructor(event: Event) : this(
        id = event.id,
        name = event.name,
        description = event.description,
        date = EventDetailUiState.dateString(event.startDate, event.endDate) ?: "",
        isLive = EventDetailUiState.isActive(event.startDate, event.endDate)
    )

}

@Composable
fun EventOverviewList(items: List<EventUi>, onSelectEvent: (Int) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(items) {
            EventRow(event = it,
                modifier = Modifier
                    .clickable { onSelectEvent(it.id) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }

}

@Preview
@Composable
fun EventOverviewListPreview() {

    val items = listOf(
        EventUi(
            id = 1,
            name = "Sonderausstellung: Mond.Landung",
            description = "Mit Virtual Reality das Weltallerobern. Am 21. Juli 1969 um 3:56 MEZ betraten die ersten beiden Menschen den Mond: Neil Armstrong und Buzz Aldrin. 500 Millionen Menschen verfolgten Armstrongs ersten Schritt auf dem pudrigen Boden gebannt am Fernsehbildschirm. Die Mission Apollo 11 war ein Erfolg.",
            date = "Mo, 19. Mai 2022"
        ),
        EventUi(
            id = 2,
            name = "Open Air Lichtspiele im Schlosshof",
            description = "",
            date = "Di, 20. Mai 2022"
        ),
        EventUi(
            id = 3,
            name = "Fahrradtour mit dem Bürgermeister",
            description = "",
            date = "Mi, 21. Mai 2022"
        )
    )

    MeinMoersTheme {
        EventOverviewList(items, onSelectEvent = {})
    }
}