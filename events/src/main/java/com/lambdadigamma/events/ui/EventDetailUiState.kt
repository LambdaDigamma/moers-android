package com.lambdadigamma.events.ui

import com.lambdadigamma.events.models.Event
import java.text.SimpleDateFormat
import java.util.*

const val eventActiveMinuteThreshold = 30

data class TimeComponent(
    val day: String,
    val date: String,
    val startTime: String,
    val endTime: String
)

data class EventDetailUiState(
    val id: Int,
    val name: String,
    val description: String?,
    val date: String,
    val location: String? = null,
    val isLive: Boolean = false,
    val categories: List<String> = listOf(),
) {

    constructor(event: Event) : this(
        id = event.id,
        name = event.name,
        description = event.description,
        date = dateString(event.startDate, event.endDate) ?: "",
        location = event.extras?.location,
        isLive = isActive(event.startDate, event.endDate),
        categories = (event.category ?: "").split(",").map { it.trim() }
    )

    companion object {

        fun isActive(startDate: Date?, endDate: Date?): Boolean {
            val current = Date()

            if (startDate != null && endDate != null) {
                return current >= startDate && current <= endDate
            }

            startDate?.let { start ->

                val calendar = Calendar.getInstance()
                calendar.time = start
                calendar.add(Calendar.MINUTE, eventActiveMinuteThreshold)

                return start <= current && calendar.time >= current

            }

            return false
        }

        fun dateString(startDate: Date?, endDate: Date?): String? {

            startDate?.let { start ->

                val timeComponents = timeComponent(start, endDate)

                endDate?.let { end ->
                    return "${timeComponents.day}, ${timeComponents.date} ${timeComponents.startTime}-${timeComponents.endTime}"
                }

                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.time = start

                return if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
                    "${timeComponents.day}, ${timeComponents.date}"
                } else {
                    "${timeComponents.day}, ${timeComponents.date} ${timeComponents.startTime}"
                }

            }

            return null
        }

        private fun timeComponent(startDate: Date, endDate: Date?): TimeComponent {

            val day = SimpleDateFormat("EEEE", Locale.getDefault())
                .format(startDate)
                .subSequence(0, 2)
                .toString()

            val date = SimpleDateFormat("dd.MM.", Locale.getDefault()).format(startDate)
            val startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(startDate)

            endDate?.let { end ->
                val endTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(end)
                return TimeComponent(day, date, startTime, endTime)
            }

            return TimeComponent(day, date, startTime, "")
        }

    }

}