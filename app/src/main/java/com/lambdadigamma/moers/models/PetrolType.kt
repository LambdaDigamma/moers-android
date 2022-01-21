package com.lambdadigamma.moers.models

enum class PetrolType(val value: String) {
    DIESEL("Diesel"),
    E5("E5"),
    E10("E10")
}

fun PetrolType.localizedName(): String {
    return when (this) {
        PetrolType.DIESEL -> "Diesel"
        PetrolType.E5 -> "E5"
        PetrolType.E10 -> "E10"
    }
}