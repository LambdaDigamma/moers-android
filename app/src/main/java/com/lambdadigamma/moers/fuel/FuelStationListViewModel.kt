package com.lambdadigamma.moers.fuel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lambdadigamma.core.Resource
import com.lambdadigamma.fuel.FuelService
import com.lambdadigamma.fuel.FuelSorting
import com.lambdadigamma.fuel.FuelType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FuelStationListViewModel @Inject constructor() : ViewModel() {

    private val fuelService: FuelService = FuelService.getPetrolService()

    fun load(): LiveData<Resource<List<FuelStationUiState>>> {
        Log.d("Api", "Loading fuel stations")
        return Transformations.map(
            fuelService.getPetrolStations(
                latitude = 51.453229,
                longitude = 6.627673,
                radius = 1.0,
                FuelSorting.DISTANCE.value,
                FuelType.DIESEL.apiValue()
            )
        ) {
            it.transform { response ->
                response.stations.orEmpty().map { station ->
                    FuelStationUiState(
                        1,
                        brand = station.brand,
                        name = station.name,
                        price = station.price ?: 0.0,
                    )
                }
            }
        }
    }

}