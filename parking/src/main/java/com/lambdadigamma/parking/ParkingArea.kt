package com.lambdadigamma.parking

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import java.util.*

enum class ParkingAreaOpeningState(val value: String) {
    @SerializedName("open")
    OPEN("open"),

    @SerializedName("closed")
    CLOSED("closed"),

    @SerializedName("unknown")
    UNKNOWN("unknown")
}

@StringRes
fun ParkingAreaOpeningState.localizedName(): Int {
    return when (this) {
        ParkingAreaOpeningState.OPEN -> R.string.parking_area_open
        ParkingAreaOpeningState.CLOSED -> R.string.parking_area_closed
        ParkingAreaOpeningState.UNKNOWN -> R.string.parking_area_unknown
    }
}

data class ParkingArea(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("current_opening_state") val currentOpeningState: ParkingAreaOpeningState,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("occupied_sites") val occupiedSites: Int,
    @SerializedName("created_at") val createdAt: Date?,
    @SerializedName("updated_at") val updatedAt: Date?
    //"location": {
    //    "type": "Point",
    //    "coordinates": [
    //    6.631362,
    //    51.449781
    //    ]
    //},
)