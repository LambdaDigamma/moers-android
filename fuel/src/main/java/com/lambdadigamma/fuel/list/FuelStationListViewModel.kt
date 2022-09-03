package com.lambdadigamma.fuel.list

import android.util.Log
import androidx.lifecycle.*
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.LocationService
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.core.geo.toPoint
import com.lambdadigamma.fuel.data.FuelRepository
import com.lambdadigamma.fuel.data.FuelService
import com.lambdadigamma.fuel.data.FuelSorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelStationListViewModel @Inject constructor(
    private val locationUpdates: LocationUpdatesUseCase,
    private val fuelService: FuelService,
    private val fuelRepository: FuelRepository,
    private val locationService: LocationService
) : ViewModel() {

    val fuelType = fuelRepository.fuelType.asLiveData(viewModelScope.coroutineContext)

    private val _fuelStations = MutableLiveData<Resource<List<FuelStationUiState>>>()
    val fuelStations: LiveData<Resource<List<FuelStationUiState>>> get() = _fuelStations

    fun reload() {

        viewModelScope.launch {

            val point = locationService.awaitLastLocation().toPoint()

            fuelRepository
                .getFuelStationList(point)
                .collect { _fuelStations.value = it }
        }

    }

    fun load(): LiveData<Resource<List<FuelStationUiState>>> {

        Log.d("Api", "Loading fuel stations")

        val locationUpdates =
            locationUpdates.fetchUpdates(5 * 60).asLiveData(viewModelScope.coroutineContext)

        return Transformations.switchMap(fuelType) { fuelType ->
            return@switchMap Transformations.switchMap(locationUpdates) { point ->
                Transformations.map(
                    fuelService.getFuelStations(
                        latitude = point.latitude,
                        longitude = point.longitude,
                        radius = 10.0,
                        sorting = FuelSorting.DISTANCE.value,
                        type = fuelType.apiValue()
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

}