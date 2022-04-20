package com.lambdadigamma.moers.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    val isOnboardingComplete = onboardingRepository.isOnboarded
    val isCitizen = true

    fun setFinished() {
        viewModelScope.launch {
            onboardingRepository.setOnboarded()
        }
    }

}