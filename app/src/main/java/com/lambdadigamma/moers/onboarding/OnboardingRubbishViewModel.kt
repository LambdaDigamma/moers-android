package com.lambdadigamma.moers.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambdadigamma.moers.data.rubbish.RubbishRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OnboardingRubbishViewModel(
    private val rubbishRepository: RubbishRepository
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
        }
    }

    init {
//        rubbishRepository.loadStreets()
    }

}