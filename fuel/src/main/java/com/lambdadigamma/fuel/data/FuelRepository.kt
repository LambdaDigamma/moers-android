package com.lambdadigamma.fuel.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.lambdadigamma.core.AppExecutors
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.Point
import com.lambdadigamma.core.utils.dataStore
import com.lambdadigamma.fuel.list.FuelStationUiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val FUEL_TYPE_KEY = "fuel_type"

class FuelRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appExecutors: AppExecutors,
    private val fuelService: FuelService
) {

    private val dataStore: DataStore<Preferences> = context.dataStore

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

    companion object {
        val fuelTypeKey = stringPreferencesKey(FUEL_TYPE_KEY)
        val defaultFuelType = FuelType.DIESEL
    }

    val fuelType: Flow<FuelType> = dataStore.data
        .map { settings ->
            settings[fuelTypeKey]?.let {
                try {
                    FuelType.valueOf(it)
                } catch (e: Exception) {
                    defaultFuelType
                }
            } ?: defaultFuelType
        }

    suspend fun updateFuelType(fuelType: FuelType) {
        dataStore.edit { preferences ->
            preferences[fuelTypeKey] = fuelType.value
        }
    }

}