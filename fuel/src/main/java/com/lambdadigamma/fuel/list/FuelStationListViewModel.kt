package com.lambdadigamma.fuel.list

import android.util.Log
import androidx.lifecycle.*
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.fuel.data.FuelService
import com.lambdadigamma.fuel.data.FuelSorting
import com.lambdadigamma.fuel.data.FuelType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FuelStationListViewModel @Inject constructor(
    private val locationUpdates: LocationUpdatesUseCase,
    private val fuelService: FuelService
) : ViewModel() {

    fun load(): LiveData<Resource<List<FuelStationUiState>>> {
        Log.d("Api", "Loading fuel stations")

        val locationUpdates =
            locationUpdates.fetchUpdates(5 * 60).asLiveData(viewModelScope.coroutineContext)

        return Transformations.switchMap(locationUpdates) { point ->
            return@switchMap Transformations.map(
                fuelService.getFuelStations(
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
                            id = station.id,
                            brand = station.brand.trim(),
                            name = station.name.trim(),
                            point = station.coordinate,
                            price = station.price ?: 0.0,
                            distance = station.dist
                        )
                    }
                }
            }
        }

    }

}