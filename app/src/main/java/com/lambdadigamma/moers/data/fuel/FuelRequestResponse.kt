package com.lambdadigamma.moers.data.fuel

data class FuelRequestResponse(
    val ok: Boolean,
    val license: String,
    val data: String,
    val status: String,
    val message: String?,
    val stations: ArrayList<FuelStation>?
)