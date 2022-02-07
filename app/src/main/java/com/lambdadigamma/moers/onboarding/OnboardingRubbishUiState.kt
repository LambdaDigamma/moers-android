package com.lambdadigamma.moers.onboarding

data class OnboardingRubbishUiState(
    val rubbishStreets: List<RubbishStreetUiState> = listOf()
)

data class RubbishStreetUiState(
    val streetName: String,
    val addition: String? = null
)