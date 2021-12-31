package com.lambdadigamma.moers.rubbish

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.google.gson.annotations.SerializedName

//@Entity(tableName = "rubbishStreets")
//data class RubbishCollectionStreet(
//    @PrimaryKey @SerializedName("id") var id: Int,
//    @SerializedName("name") val street: String,
//    @Nullable @SerializedName("street_addition") val streetAddition: String?,
//    @SerializedName("residual_tour") val residualWaste: Int,
//    @SerializedName("organic_tour") val organicWaste: Int,
//    @SerializedName("paper_tour") val paperWaste: Int,
//    @SerializedName("plastic_tour") val yellowBag: Int,
//    @SerializedName("cuttings_tour") val greenWaste: Int,
//    @Nullable @SerializedName("Kehrtag") val sweeperDay: String? = "",
//    @SerializedName("year") val year: Int
//) {
//
//    val displayName: String
//        get() {
//            return "${this.street} ${this.streetAddition ?: ""}"
//        }
//
//}