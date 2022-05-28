package com.lambdadigamma.moers.onboarding

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambdadigamma.core.geo.DefaultGeocodingService
import com.lambdadigamma.core.geo.GMSLocationService
import com.lambdadigamma.core.geo.GeocodingService
import com.lambdadigamma.moers.Application
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OnboardingRubbishViewModel(
    private val rubbishRepository: com.lambdadigamma.rubbish.RubbishRepository
) : ViewModel() {

    var uiState by mutableStateOf(OnboardingRubbishUiState())
        private set

    private var locationService: GMSLocationService
    private var geocodingService: GeocodingService
    private var loadingJob: Job? = null

    init {
        this.locationService = GMSLocationService(Application.instance)
        this.geocodingService = DefaultGeocodingService(Application.instance)
    }

    val searchTerm = MutableStateFlow("")

    fun updateSearchTerm(searchTerm: String) {
        this.searchTerm.value = searchTerm
        this.load(searchTerm = searchTerm)
    }

    fun load(searchTerm: String? = null) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            val streets = rubbishRepository.loadStreets(streetName = searchTerm).map { street ->
                RubbishStreetUiState(
                    street.id,
                    street.name,
                    street.streetAddition
                )
            }
            uiState = uiState.copy(rubbishStreets = streets)
        }
    }

    suspend fun loadLocation() {

        locationService.loadLastLocation()?.let { point ->
            val address = geocodingService.reverseGeocode(point)
            Log.d("ViewModels", "address: $address")
            address.firstOrNull()?.let { geocodedAddress ->
                this.load(geocodedAddress.street)
                this.searchTerm.value = geocodedAddress.street
            }
        }

    }

    fun selectStreet(street: RubbishStreetUiState) {
        viewModelScope.launch {
            rubbishRepository.setStreet(street.id, street.streetName)
        }
    }

}