package com.lambdadigamma.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddressContainer(
    modifier: Modifier = Modifier,
    label: String? = null,
//    entries: List<OpeningHoursEntry>
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        label?.let {
            Text(text = it, fontWeight = FontWeight.Bold)
        }
//        for (entry in entries) {
//            OpeningEntryRow(label = entry.label, value = entry.value)
//        }
    }

}