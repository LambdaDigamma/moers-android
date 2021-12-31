package com.lambdadigamma.moers.rubbish

import java.text.SimpleDateFormat
import java.util.*

fun parseDate(date: String, format: String): Date? {

    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(date)

}

data class RubbishCollectionItem(
    val date: String,
    val type: RubbishWasteType
) {

    val parsedDate: Date
        get() {
            return parseDate(date, "dd.MM.yyyy") ?: Date()
        }

}