package com.lambdadigamma.fuel.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lambdadigamma.core.AppExecutors
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.Point
import com.lambdadigamma.fuel.list.FuelStationUiState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FuelRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appExecutors: AppExecutors,
    private val fuelService: FuelService
) {

    fun load(point: Point): LiveData<Resource<List<FuelStationUiState>>> {
        return Transformations.map(
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
                        id = station.id,
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