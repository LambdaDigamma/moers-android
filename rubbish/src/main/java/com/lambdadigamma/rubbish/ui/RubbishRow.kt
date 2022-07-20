package com.lambdadigamma.rubbish.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.core.DateUtils
import com.lambdadigamma.core.theme.MeinMoersTheme
import com.lambdadigamma.rubbish.R.drawable
import com.lambdadigamma.rubbish.RubbishCollectionItem
import com.lambdadigamma.rubbish.RubbishWasteType
import com.lambdadigamma.rubbish.localizedName
import java.time.format.FormatStyle

@SuppressLint("NewApi")
@Composable
fun RubbishRow(item: RubbishCollectionItem, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = modifier.fillMaxWidth()
    ) {

        drawable.ic_rubbish_paper

        val resource = when (item.type) {
            RubbishWasteType.RESIDUAL -> drawable.ic_rubbish_residual
            RubbishWasteType.ORGANIC -> drawable.ic_rubbish_organic
            RubbishWasteType.PAPER -> drawable.ic_rubbish_paper
            RubbishWasteType.PLASTIC -> drawable.ic_rubbish_plastic
            RubbishWasteType.CUTTINGS -> drawable.ic_rubbish_organic
        }

        val formattedDate = DateUtils.format(item.parsedDate, FormatStyle.MEDIUM)

        Image(
            painter = painterResource(id = resource),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {
            Text(text = item.type.localizedName(), fontWeight = FontWeight.Medium)
            Text(text = formattedDate)
        }
    }
}


@Preview
@Composable
fun RubbishRow_Preview() {
    MeinMoersTheme {
        RubbishRow(
            item = RubbishCollectionItem(
                id = 1,
                type = RubbishWasteType.RESIDUAL,
                date = "01.02.2022"
            )
        )
    }
}
