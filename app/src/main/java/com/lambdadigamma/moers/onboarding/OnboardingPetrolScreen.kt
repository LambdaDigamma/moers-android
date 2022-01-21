package com.lambdadigamma.moers.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.models.PetrolType
import com.lambdadigamma.moers.models.localizedName
import com.lambdadigamma.moers.onboarding.ui.OnboardingHost
import com.lambdadigamma.moers.ui.theme.LegacyMeinMoersTheme
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme

@Composable
fun OnboardingPetrolScreen() {

    val currentPetrolType = remember {
        mutableStateOf(PetrolType.DIESEL)
    }

    OnboardingHost(
        title = "Kraftstoff",
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

//                Image(
//                    painter = painterResource(id = R.drawable.onboarding_location),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth()
//                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    
                    Text(
                        text = "Wähle einen Kraftstofftyp",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Die App kann Dir einen Überblick über die aktuellen Krafstoffpreise geben. Wähle dazu Deinen bevorzugten Kraftstofftyp aus und fahre fort.",
                        textAlign = TextAlign.Left,
                    )

                    Column() {

                        PetrolType.values().forEach { petrolType ->

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                RadioButton(
                                    selected = currentPetrolType.value == petrolType,
                                    onClick = {
                                        currentPetrolType.value = petrolType
                                    }
                                )

                                Text(text = petrolType.localizedName())

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
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Text(
                        text = "Weiter",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    )

}

@Preview
@Composable
fun OnboardingPetrolPreview() {
    MeinMoersTheme {
        LegacyMeinMoersTheme {
            OnboardingPetrolScreen()
        }
    }
}