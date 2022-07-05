package com.lambdadigamma.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDate(date: Date): LocalDate {
        return Instant.ofEpochMilli(date.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDate(date: Date, zoneId: ZoneId?): LocalDate {
        return Instant.ofEpochMilli(date.time)
            .atZone(zoneId)
            .toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDate(date: LocalDate): Date {
        return Date.from(
            date
                .atStartOfDay().atZone(
                    ZoneId.systemDefault()
                )
                .toInstant()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDate(date: LocalDate, zoneId: ZoneId?): Date {
        return Date.from(
            date
                .atStartOfDay().atZone(
                    zoneId
                )
                .toInstant()
        )
    }

    fun format(date: Date, formatStyle: FormatStyle): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateFormatter: DateTimeFormatter =
                DateTimeFormatter.ofLocalizedDate(formatStyle)
            val localDate = this.convertToLocalDate(date)
            return localDate.format(dateFormatter)
        } else {
            return ""
        }

    }

}