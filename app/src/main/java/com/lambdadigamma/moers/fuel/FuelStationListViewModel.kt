package com.lambdadigamma.moers.fuel

import android.util.Log
import androidx.lifecycle.*
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.fuel.FuelService
import com.lambdadigamma.fuel.FuelSorting
import com.lambdadigamma.fuel.FuelType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FuelStationListViewModel @Inject constructor(
    private val locationUpdates: LocationUpdatesUseCase,
) : ViewModel() {

    private val fuelService: FuelService = FuelService.getPetrolService()

    fun load(): LiveData<Resource<List<FuelStationUiState>>> {
        Log.d("Api", "Loading fuel stations")

        val locationUpdates =
            locationUpdates.fetchUpdates(5 * 60).asLiveData(viewModelScope.coroutineContext)

        return Transformations.switchMap(locationUpdates) { point ->
            return@switchMap Transformations.map(
                fuelService.getPetrolStations(
                    latitude = point.latitude,
                    longitude = point.longitude,
                    radius = 10.0,
                    FuelSorting.DISTANCE.value,
                    FuelType.DIESEL.apiValue()
                )
            ) {
                it.transform { response ->
                    response.stations.orEmpty().map { station ->
                        FuelStationUiState(
                            1,
                            brand = station.brand.trim(),
                            name = station.name.trim(),
                            price = station.price ?: 0.0,
                            distance = station.dist
                        )
                    }
                }
            }
        }

    }

}