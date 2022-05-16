package com.lambdadigamma.rubbish

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

const val RUBBISH_WASTE_TYPE_KEY = "WASTE_TYPE"

fun localizedRubbishWasteType(type: RubbishWasteType): String {

    when (type) {
        RubbishWasteType.RESIDUAL -> {
            return "Restabfall"
        }
        RubbishWasteType.ORGANIC -> {
            return "Biotonne"
        }
        RubbishWasteType.PAPER -> {
            return "Papiertonne"
        }
        RubbishWasteType.PLASTIC -> {
            return "Gelber Sack"
        }
        RubbishWasteType.CUTTINGS -> {
            return "Grünschnitt"
        }
    }

}

enum class RubbishWasteType(val value: String) {
    @SerializedName("residual")
    RESIDUAL("residual"),

    @SerializedName("organic")
    ORGANIC("organic"),

    @SerializedName("paper")
    PAPER("paper"),

    @SerializedName("plastic")
    PLASTIC("plastic"),

    @SerializedName("cuttings")
    CUTTINGS("cuttings")
}

fun RubbishWasteType.localizedName(): String {
    return when (this) {
        RubbishWasteType.RESIDUAL -> "Restabfall"
        RubbishWasteType.ORGANIC -> "Biotonne"
        RubbishWasteType.PAPER -> "Papiertonne"
        RubbishWasteType.PLASTIC -> "Gelber Sack"
        RubbishWasteType.CUTTINGS -> "Grünschnitt"
    }
}

fun RubbishWasteType.shortName(): String {
    return when (this) {
        RubbishWasteType.PAPER -> "Papier"
        RubbishWasteType.ORGANIC -> "Bio"
        RubbishWasteType.PLASTIC -> "Gelb"
        RubbishWasteType.RESIDUAL -> "Rest"
        RubbishWasteType.CUTTINGS -> "Schnitt"
    }
}

fun RubbishWasteType.color(): Color {
    return when (this) {
        RubbishWasteType.RESIDUAL -> Color.Gray
        RubbishWasteType.PLASTIC -> Color.Yellow
        RubbishWasteType.ORGANIC -> Color.Green
        RubbishWasteType.CUTTINGS -> Color.Green
        RubbishWasteType.PAPER -> Color.Blue
    }
}

fun RubbishWasteType.backgroundColor(): Color {
    return this.color().copy(0.2f)
}