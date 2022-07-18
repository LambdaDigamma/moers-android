package com.lambdadigamma.moers.fuel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FuelStationUiState(val id: Int, val brand: String, val name: String, val price: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationList(items: List<FuelStationUiState>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column() {
                    Text(text = item.brand, fontWeight = FontWeight.Bold)
                    Text(text = item.name.trim())
                }
                Card(
                    modifier = Modifier
//                        .background(Color.Red)
                        .clipToBounds(),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {

                        val price = item.price.toString()
                        val pricePart = price.substring(0, price.length - 1)
                        val superscript = SpanStyle(
                            baselineShift = BaselineShift.Superscript,
                            fontSize = 12.sp,
                        )

                        Text(
                            text = buildAnnotatedString {
                                append(pricePart)
                                withStyle(superscript) {
                                    append("9")
                                }
                            },
                            fontWeight = FontWeight.Bold,
                            style = LocalTextStyle.current.copy(fontFeatureSettings = "tnum"),
                        )
                    }
                }
            }
        }
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Divider(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "CC BY 4.0 - https://creativecommons.tankerkoenig.de",

                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun FuelStationListPreview() {
    FuelStationList(
        items = listOf(
            FuelStationUiState(id = 1, brand = "Markant", name = "Yadigar Güner", price = 1.599),
            FuelStationUiState(id = 2, brand = "SB", name = "Sb Moers", price = 1.609),
            FuelStationUiState(id = 3, brand = "JET", name = "MOERS", price = 1.619),
            FuelStationUiState(id = 4, brand = "CLASSIC", name = "Tankstelle Moers", price = 1.619),
            FuelStationUiState(
                id = 5,
                brand = "Kuster Energy",
                name = "Kuster Energy",
                price = 1.659
            ),
        )
    )
}