package com.lambdadigamma.moers.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingTopBar(text: String) {

    val baseSteps = arrayOf(
        OnboardingStep("Privacy", OnboardingStepState.COMPLETE),
        OnboardingStep("Rubbish", OnboardingStepState.IN_PROGRESS),
        OnboardingStep("Petrol", OnboardingStepState.NOT_COMPLETE),
        OnboardingStep("Finish", OnboardingStepState.NOT_COMPLETE),
    )


    val steps = remember {
        mutableStateOf(baseSteps)
    }

    Card(shape = RectangleShape, elevation = 4.dp) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
            OnboardingSteps()
        }
    }

}

@Composable
@Preview
fun OnboardingTopBarPreview() {
    OnboardingTopBar("Onboarding")
}