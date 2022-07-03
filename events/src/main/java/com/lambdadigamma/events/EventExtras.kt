package com.lambdadigamma.mmapi.models.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventExtras(@PrimaryKey var id: Int = 0,
                       val needsFestivalTicket: Boolean?,
                       val isFree: Boolean?,
                       val visitWithExtraTicket: Boolean?,
                       @ColumnInfo(name = "extra_organizer") var organizer: String?,
                       @ColumnInfo(name = "extra_location") val location: String?,
                       @ColumnInfo(name = "extra_street") val street: String?,
                       @ColumnInfo(name = "extra_houseNumber") val houseNumber: String?,
                       @ColumnInfo(name = "extra_place") val place: String?,
                       @ColumnInfo(name = "extra_postcode") val postcode: String?,
                       @ColumnInfo(name = "extra_lat") val lat: Double?,
                       @ColumnInfo(name = "extra_lng") val lng: Double?,
                       @ColumnInfo(name = "extra_descriptionEN") val descriptionEN: String?,
                       @ColumnInfo(name = "extra_iconURL") val iconURL: String?,
                       @ColumnInfo(name = "extra_moving_act") val isMovingAct: Boolean?,
                       val color: String?)