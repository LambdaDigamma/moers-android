package com.lambdadigamma.moers.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.onboarding.ui.OnboardingStepState
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class OnboardingStep constructor(val value: Int) {
    ABOUT(0),
    USER_TYPE(1),
    LOCATION(2),
    PETROL(3),
    DONE(4)
}

@StringRes
fun OnboardingStep.displayName(): Int {
    return when (this) {
        OnboardingStep.ABOUT -> R.string.onboarding_about_title
        OnboardingStep.USER_TYPE -> R.string.onboarding_user_type_title
        OnboardingStep.LOCATION -> R.string.onboarding_location_title
        OnboardingStep.PETROL -> R.string.feature_petrol_title
        OnboardingStep.DONE -> R.string.onboarding_done_title
    }
}

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {

    val currentStep = "Hallo!"

    val isOnboardingComplete = onboardingRepository.isOnboarded
    val isCitizen = true

    private var currentOnboardingStep = MutableStateFlow(OnboardingStep.ABOUT)

    val currentOnboardingState = currentOnboardingStep.map {
        val steps = mutableListOf<OnboardingStepState>()

        // Adds completed steps to the list
        steps.addAll(
            generateSequence { OnboardingStepState.COMPLETE }.take(
                it.value
            )
        )

        // Adds current step to the list
        steps.add(OnboardingStepState.IN_PROGRESS)

        // Adds remaining steps to the list
        steps.addAll(
            generateSequence { OnboardingStepState.NOT_COMPLETE }.take(OnboardingStep.values().size - (currentOnboardingStep.value.value + 1))
        )

        return@map OnboardingTopBarUiState(
            currentOnboardingStep.value.displayName(),
            steps
        )
    }

    fun iterateNextStep() {
        if (currentOnboardingStep.value == OnboardingStep.DONE) {
            return
        }
        OnboardingStep.values().indexOf(currentOnboardingStep.value).let {
            currentOnboardingStep.value = OnboardingStep.values()[it + 1]
        }
    }

    fun setFinished() {
        viewModelScope.launch {
            onboardingRepository.setOnboarded()
        }
    }

}