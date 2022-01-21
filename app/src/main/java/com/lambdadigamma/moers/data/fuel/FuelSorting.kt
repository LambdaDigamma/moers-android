package com.lambdadigamma.moers.data.fuel

/**
 * The Tankerkönig api offers sorting fuel stations
 * either distance or price.
 */
enum class FuelSorting(val value: String) {
    DISTANCE("dist"),
    PRICE("price")
}