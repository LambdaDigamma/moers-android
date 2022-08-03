package com.lambdadigamma.events.ui

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.core.ui.ResourcefulContent
import com.lambdadigamma.events.R

@Composable
fun EventDetailScreen(id: Int, onBack: () -> Unit) {

    val viewModel: EventDetailViewModel = hiltViewModel()
    viewModel.eventId = id
    val event = viewModel.load()

    ResourcefulContent(resource = event, onLoad = { /*TODO*/ }) {
        EventDetail(
            event = EventDetailUiState(it),
            onBack = onBack
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventDetail(event: EventDetailUiState, onBack: () -> Unit) {

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, event.url)
        putExtra(Intent.EXTRA_TITLE, event.name)
        type = "text/plain"
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Details")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            context.startActivity(sendIntent)
                        },
                        content = {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null)
                        }
                    )
                }
            )

//            LargeTopAppBar(title = { Text(text = event.title) })
//            EventDetailTopBar(event)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = event.name,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.headlineSmall,
                        lineHeight = MaterialTheme.typography.headlineSmall.lineHeight * 0.9
                    )

                    Text(
                        text = "${event.location} • ${event.date}",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    event.description?.let { description ->
                        SelectionContainer {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                }

                LocationRow(location = event.location)

                OrganiserRow(organizer = event.organizer)

                Categories(
                    categories = event.categories,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                )

            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Categories(categories: List<String>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {

//        val categories = listOf<String>(
//            "Ausstellungen",
//            "Information und Weiterbildung",
//            "Information",
//            "Weiterbildung"
//        )

        for (category in categories) {
            SuggestionChip(onClick = { /*TODO*/ }, label = {
                Text(text = category)
            })
        }

    }
}

@Composable
private fun LocationRow(location: String?) {

    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_location_on_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Veranstaltungsort",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = location ?: "Unbekannt",
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

}

@Composable
private fun OrganiserRow(organizer: String?) {

//    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_home_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Organisator",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = organizer ?: "Unbekannt",
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

}

@Preview(showBackground = true)
@Composable
fun EventDetailScreenPreview() {
    MeinMoersTheme {
        EventDetail(
            EventDetailUiState(
                id = 1,
                name = "Sonderausstellung: Mond.Landung",
                description = "Mit Virtual Reality das Weltall erobern. Am 21. Juli 1969 um 3:56 MEZ betraten die ersten beiden Menschen den Mond: Neil Armstrong und Buzz Aldrin. 500 Millionen Menschen verfolgten Armstrongs ersten Schritt auf dem pudrigen Boden gebannt am Fernsehbildschirm. Die Mission Apollo 11 war ein Erfolg.",
                date = "Mo, 19. Mai 2022"
            ),
            onBack = {}
        )
    }
}