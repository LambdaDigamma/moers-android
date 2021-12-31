package com.lambdadigamma.moers.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBar

enum class UserType {
    CITIZEN,
    VISITOR
}

// Maybe later this could be become the hometown selection?
@Composable
fun OnboardingUserTypeScreen() {

    val selectedUserType = remember { mutableStateOf(UserType.CITIZEN) }
    val scrollState = rememberScrollState()

    Column {

        OnboardingTopBar(text = stringResource(R.string.onboarding_user_type_title))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(R.string.onboarding_user_type_info),
                    style = MaterialTheme.typography.bodyLarge
                )

                Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedUserType.value == UserType.CITIZEN,
                            onClick = { selectedUserType.value = UserType.CITIZEN })
                        Text(text = stringResource(R.string.onboarding_user_type_citizen))
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedUserType.value == UserType.VISITOR,
                            onClick = { selectedUserType.value = UserType.VISITOR })
                        Text(text = stringResource(R.string.onboarding_user_type_visitor))
                    }
                }

//                Spacer(modifier = Modifier.height(24.dp))

            }

            Column(
                modifier = Modifier
            ) {
                Divider()
                Surface(tonalElevation = 1.dp) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Aktivierte Funktionen",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Card(
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Column {

                                FeatureRow(
                                    title = stringResource(R.string.feature_petrol_title),
                                    text = stringResource(R.string.feature_petrol_text),
                                    checked = true
                                )

                                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

                                FeatureRow(
                                    title = stringResource(R.string.feature_parking_title),
                                    text = stringResource(R.string.feature_parking_text),
                                    checked = true
                                )

                                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

                                FeatureRow(
                                    title = stringResource(R.string.feature_waste_schedule_title),
                                    text = stringResource(R.string.feature_waste_schedule_text),
                                    checked = selectedUserType.value == UserType.CITIZEN
                                )

                                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

                                FeatureRow(
                                    title = stringResource(R.string.feature_transit_title),
                                    text = stringResource(R.string.feature_transit_text),
                                    checked = true
                                )

                            }

                        }

                        Text(
                            stringResource(R.string.onboarding_user_type_features_footer),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                    }

                }
            }
        }


        Divider()

        Column(modifier = Modifier.padding(16.dp)) {

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.onboarding_user_type_cta),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FeatureRow(title: String, text: String, checked: Boolean = false) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .weight(1f)
                .alpha(if (checked) 1f else 0.3f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }

        Box(modifier = Modifier.size(24.dp)) {

            if (checked) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

        }


    }

}