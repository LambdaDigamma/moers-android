package com.lambdadigamma.moers.onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lambdadigamma.moers.onboarding.OnboardingViewModel

@Composable
fun OnboardingHost(
    title: String,
    shouldScroll: Boolean = true,
    content: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit
) {

    val viewModel: OnboardingViewModel = hiltViewModel()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {

        OnboardingTopBar(viewModel.currentOnboardingStepState())

        Column(
            modifier = if (shouldScroll) {
                Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            } else Modifier.weight(1.0f)
        ) {
            content()
        }

        Divider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            bottomContent()
        }

    }

}

@Composable
@Preview
fun OnboardingHostPreview() {
    OnboardingHost(title = "About", content = { /*TODO*/ }) {
    }
}