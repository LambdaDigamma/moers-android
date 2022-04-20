package com.lambdadigamma.fuel

/**
 * Currently supported are three fuel types:
 * diesel, e5 and e10.
 */
enum class FuelType(val value: String) {
    DIESEL("Diesel"),
    E5("E5"),
    E10("E10");

    fun localizedName(): String {
        return when (this) {
            DIESEL -> "Diesel"
            E5 -> "E5"
            E10 -> "E10"
        }
    }
}