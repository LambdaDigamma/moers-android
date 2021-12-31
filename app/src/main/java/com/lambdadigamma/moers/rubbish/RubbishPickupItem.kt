package com.lambdadigamma.moers.rubbish

//import androidx.room.Entity
//import com.google.gson.annotations.SerializedName
import java.util.*

//@Entity(tableName = "rubbishPickupItems", primaryKeys = ["date", "type"])
data class RubbishPickupItem(
//    @SerializedName("date")
    val date: Date,
//    @SerializedName("type")
    val type: RubbishWasteType
)