package com.lambdadigamma.fuel.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.lambdadigamma.core.Resource
import com.lambdadigamma.core.geo.GMSLocationService
import com.lambdadigamma.core.geo.GeocodingService
import com.lambdadigamma.core.geo.LocationUpdatesUseCase
import com.lambdadigamma.fuel.R
import com.lambdadigamma.fuel.data.FuelRepository
import com.lambdadigamma.fuel.data.FuelService
import com.lambdadigamma.fuel.data.FuelSorting
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class DashboardFuelViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationUpdates: LocationUpdatesUseCase,
    private val geocodingService: GeocodingService,
    private val repository: FuelRepository,
) : ViewModel() {

    private val locationService: GMSLocationService
    private val fuelService: FuelService = FuelService.getFuelService()

    init {
        this.locationService = GMSLocationService(context)
//        load()
    }

    fun load(): LiveData<Resource<FuelDashboardData>> {

//        val location =
//            locationService.loadLastLocation().asLiveData(viewModelScope.coroutineContext)

        val location = locationUpdates.fetchUpdates(60 * 5)
            .asLiveData(viewModelScope.coroutineContext)

        location.observeForever {
            Log.d("DashboardFuelViewModel", "location: $it")
        }

        val fuelType = repository.fuelType.asLiveData(viewModelScope.coroutineContext)

        return Transformations.switchMap(fuelType) { fuelType ->
            return@switchMap Transformations.switchMap(location) { receivedLocation ->

                val locationTitle = geocodingService.reverseGeocode(receivedLocation)
                    .firstOrNull()?.place
                    ?: this.context.getString(R.string.fuel_dashboard_location_unknown)

                Transformations.map(
                    fuelService.getFuelStations(
                        latitude = receivedLocation.latitude,
                        longitude = receivedLocation.longitude,
                        radius = 10.0,
                        sorting = FuelSorting.DISTANCE.value,
                        type = fuelType.apiValue()
                    )
                ) { resource ->
                    resource.transform { response ->

                        val notNull = response.stations.orEmpty().mapNotNull {
                            it.price
                        }

                        val average = DoubleArray(notNull.size) { i ->
                            notNull[i]
                        }.average()

                        Log.d("FuelDashboardViewModel", "L ${fuelType.localizedName().uppercase()}")

                        FuelDashboardData(
                            type = "L ${fuelType.localizedName().uppercase()}",
                            location = locationTitle,
                            price = (average * 100).roundToInt() / 100.0,
                            numberOfStations = notNull.size
                        )
                    }
                }
            }
        }


    }

}