package com.lambdadigamma.moers.onboarding

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambdadigamma.core.geo.GMSLocationService
import com.lambdadigamma.core.geo.GoogleMapsNavigationProvider
import com.lambdadigamma.moers.Application
import com.lambdadigamma.rubbish.RubbishRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OnboardingRubbishViewModel(
    private val rubbishRepository: com.lambdadigamma.rubbish.RubbishRepository
) : ViewModel() {

    var uiState by mutableStateOf(OnboardingRubbishUiState())
        private set

    private var loadingJob: Job? = null

    fun load() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            val streets = rubbishRepository.loadStreets().map {
                RubbishStreetUiState(it.name, it.streetAddition)
            }
            uiState = uiState.copy(rubbishStreets = streets)
            loadLocation()
        }
    }

    suspend fun loadLocation() {

        val service = GMSLocationService(Application.instance)
        val location = service.loadLastLocation()

        Log.d("OnboardingRubbishViewModel", "location: $location")

        location?.let {
            GoogleMapsNavigationProvider(Application.instance)
                .startNavigation(
                    it.latitude,
                    it.longitude
                )
        }

    }

    init {
//        rubbishRepository.loadStreets()
    }

}