package com.lambdadigamma.parking

import com.google.gson.annotations.SerializedName
import java.util.*

enum class ParkingAreaOpeningState(val value: String) {
    OPEN("open"),
    CLOSED("closed"),
    UNKNOWN("unknown")
}

data class ParkingArea(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("current_opening_state") val currentOpeningState: ParkingAreaOpeningState,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("occupied_sites") val occupiedSites: Int,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
    //"location": {
    //    "type": "Point",
    //    "coordinates": [
    //    6.631362,
    //    51.449781
    //    ]
    //},
)