package com.lambdadigamma.fuel.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.core.ui.AddressUiState
import com.lambdadigamma.fuel.data.FuelService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FuelStationDetailViewModel @Inject constructor(
    private val locationUpdates: LocationUpdatesUseCase,
) : ViewModel() {

    lateinit var id: String

    private val fuelService: FuelService = FuelService.getFuelService()

    fun load(): LiveData<Resource<FuelStationUiState>> {
        Log.d("Api", "Loading fuel station")

        return Transformations.map(
            fuelService.getFuelStation(id)
        ) {
            it.transform { response ->
                return@transform FuelStationUiState(
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
                )
            }
        }
    }

}