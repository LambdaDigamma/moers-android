package com.lambdadigamma.moers.onboarding.ui

import androidx.annotation.StringRes

data class OnboardingTopBarUiState(
    @StringRes val title: Int,
    val steps: List<OnboardingStepState>
)