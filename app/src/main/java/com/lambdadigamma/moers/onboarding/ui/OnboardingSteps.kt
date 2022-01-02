package com.lambdadigamma.moers.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

enum class OnboardingStepState {
    NOT_COMPLETE,
    IN_PROGRESS,
    COMPLETE
}

data class OnboardingStep(val title: String, val state: OnboardingStepState)

@Composable
fun OnboardingSteps(modifier: Modifier = Modifier) {

    val steps = arrayOf(
        OnboardingStep("Privacy", OnboardingStepState.IN_PROGRESS),
        OnboardingStep("Rubbish", OnboardingStepState.NOT_COMPLETE),
        OnboardingStep("Petrol", OnboardingStepState.NOT_COMPLETE),
        OnboardingStep("Finish", OnboardingStepState.NOT_COMPLETE),
    )
    val color = MaterialTheme.colorScheme.primary
    val circleSize = 24.dp
    val checkSize = 16.dp
    val borderWidth = 2.dp

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = borderWidth.value * density
                val y = size.height
                val width = size.width

                drawLine(
                    color,
                    Offset(0f + circleSize.toPx(), y / 2),
                    Offset(width - circleSize.toPx(), y / 2),
                    strokeWidth
                )
            }) {

        for (step in steps) {

            when (step.state) {
                OnboardingStepState.NOT_COMPLETE -> {
                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .clip(CircleShape)
                            .border(width = borderWidth, color = color, shape = CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {}
                }
                OnboardingStepState.IN_PROGRESS -> {

                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .clip(CircleShape)
                            .border(width = borderWidth, color = color, shape = CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {

                        Box(
                            modifier = Modifier
                                .size(circleSize / 2)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {}

                    }

                }
                OnboardingStepState.COMPLETE -> {

                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(checkSize)
                        )
                    }

                }
            }

        }

    }

}