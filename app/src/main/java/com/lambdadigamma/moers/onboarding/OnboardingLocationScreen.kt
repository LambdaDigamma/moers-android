package com.lambdadigamma.moers.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.onboarding.ui.OnboardingHost
import com.lambdadigamma.moers.ui.theme.LegacyMeinMoersTheme
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingLocationScreen(onContinue: () -> Unit) {

    OnboardingHost(
        title = stringResource(R.string.onboarding_location_title),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.onboarding_location),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
//                    modifier = Modifier.widthIn(0.dp, 160.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_location_info_title),
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.onboarding_location_info_intro),
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = stringResource(R.string.onboarding_location_info_transparency)
                    )
                }

            }

            Divider()

            Surface(tonalElevation = 1.dp) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    ElevatedCard {
                        Surface(tonalElevation = (-2).dp, shadowElevation = 2.dp) {
                            Column {

                                FeatureRow(
                                    title = stringResource(R.string.location_reason_fuel_title),
                                    text = stringResource(R.string.location_reason_fuel_text),
                                    standalone = true
                                )

                                Divider(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.12f
                                    )
                                )

                                FeatureRow(
                                    title = stringResource(R.string.location_reason_waste_schedule_title),
                                    text = stringResource(R.string.location_reason_waste_schedule_text),
                                    standalone = true
                                )

                            }
                        }
                    }

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Surface(tonalElevation = 4.dp) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                Text(
                                    text = stringResource(R.string.onboarding_location_no_location_disclaimer),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                }
            }
        },
        bottomContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_location_enable_cta),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.onboarding_location_not_now_cta))
                }
            }
        }
    )

}

@Preview
@Composable
fun OnboardingLocationPreview() {
    MeinMoersTheme {
        LegacyMeinMoersTheme {
            OnboardingLocationScreen(onContinue = {})
        }
    }
}