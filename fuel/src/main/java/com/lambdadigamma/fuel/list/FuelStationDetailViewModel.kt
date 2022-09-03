package com.lambdadigamma.fuel.list

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.core.ui.AddressUiState
import com.lambdadigamma.core.ui.OpeningHoursEntry
import com.lambdadigamma.fuel.data.FuelService
import com.lambdadigamma.fuel.detail.FuelStationDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FuelStationDetailViewModel @Inject constructor(
    private val locationUpdates: LocationUpdatesUseCase,
    private val fuelService: FuelService
) : ViewModel() {

    lateinit var id: String

    fun load(): LiveData<Resource<FuelStationDetailUiState>> {
        Log.d("Api", "Loading fuel station")

        return Transformations.map(
            fuelService.getFuelStation(id)
        ) { resource ->
            resource.transform { response ->

                return@transform FuelStationDetailUiState(
                    id = response.station.id,
                    brand = response.station.brand.trim(),
                    name = response.station.name.trim(),
                    point = response.station.coordinate,
                    price = response.station.price ?: 0.0,
                    distance = response.station.dist,
                    priceDiesel = response.station.diesel,
                    priceE5 = response.station.e5,
                    priceE10 = response.station.e10,
                    address = AddressUiState(
                        street = response.station.street,
                        houseNumber = response.station.houseNumber ?: "",
                        city = response.station.place,
                        state = response.station.state ?: "",
                        zip = response.station.postCode ?: ""
                    ),
                    openingHours = response.station.openingTimes.orEmpty().map {
                        val time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                            val start = LocalTime.parse(it.start).format(timeFormat)
                            val end = LocalTime.parse(it.end).format(timeFormat)
                            "${start}-${end}"
                        } else {
                            "${it.start}-${it.end}"
                        }
                        OpeningHoursEntry(
                            label = it.text,
                            value = time
                        )
                    },
                    isOpen = response.station.isOpen
                )
            }
        }
    }

}