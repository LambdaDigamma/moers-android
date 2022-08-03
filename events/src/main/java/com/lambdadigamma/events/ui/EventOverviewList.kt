package com.lambdadigamma.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.isToday
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.events.models.Event
import java.util.*

data class EventUi(
    val id: Int,
    val name: String,
    val description: String?,
    val date: String,
    val isLive: Boolean = false,
    val isToday: Boolean = false,
) {

    constructor(event: Event) : this(
        id = event.id,
        name = event.name,
        description = event.description,
        date = EventDetailUiState.dateString(event.startDate, event.endDate) ?: "",
        isLive = EventDetailUiState.isActive(event.startDate, event.endDate),
        isToday = event.startDate?.let { startDate ->
            return@let isToday(startDate) || event.endDate?.let { it > Date() && startDate < Date() } == true
        } == true
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOverviewList(items: List<EventUi>, onSelectEvent: (Int) -> Unit) {

    val today = items.filter { it.isToday }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier
                .clickable { }
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Heute",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Alle ansehen")
            }
        }

        OutlinedCard(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (event in today) {
                    EventRow(event = event,
                        modifier = Modifier
                            .clickable { onSelectEvent(event.id) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }


    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//
//        item {
//            Text(text = "Heute")
//        }
//
//        items(items) {
//            EventRow(event = it,
//                modifier = Modifier
//                    .clickable { onSelectEvent(it.id) }
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//            )
//        }
//    }

}

@Preview
@Composable
fun EventOverviewListPreview() {

    val items = listOf(
        EventUi(
            id = 1,
            name = "Sonderausstellung: Mond.Landung",
            description = "Mit Virtual Reality das Weltallerobern. Am 21. Juli 1969 um 3:56 MEZ betraten die ersten beiden Menschen den Mond: Neil Armstrong und Buzz Aldrin. 500 Millionen Menschen verfolgten Armstrongs ersten Schritt auf dem pudrigen Boden gebannt am Fernsehbildschirm. Die Mission Apollo 11 war ein Erfolg.",
            date = "Mo, 19. Mai 2022",
            isToday = true
        ),
        EventUi(
            id = 2,
            name = "Open Air Lichtspiele im Schlosshof",
            description = "",
            date = "Di, 20. Mai 2022",
            isToday = true
        ),
        EventUi(
            id = 3,
            name = "Fahrradtour mit dem Bürgermeister",
            description = "",
            date = "Mi, 21. Mai 2022"
        ),
        EventUi(
            id = 4,
            name = "Leseabend für Kinder und Jugendliche von 6-14 Jahren",
            description = "",
            date = "Mi, 21. Mai 2022",
            isToday = true,
        )
    )

    MeinMoersTheme {
        EventOverviewList(items, onSelectEvent = {})
    }
}