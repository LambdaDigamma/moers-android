package com.lambdadigamma.moers.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.onboarding.ui.OnboardingHost
import com.lambdadigamma.moers.ui.theme.LegacyMeinMoersTheme
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme
import com.lambdadigamma.rubbish.RubbishRepository
import com.lambdadigamma.rubbish.source.DefaultRubbishApiService
import com.lambdadigamma.rubbish.source.RubbishRemoteDataSource
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnboardingRubbishStreetScreen(
    onContinue: () -> Unit
) {

    val context = LocalContext.current

    val rubbishRepository = com.lambdadigamma.rubbish.RubbishRepository(
        context = context,
        remoteDataSource = com.lambdadigamma.rubbish.source.RubbishRemoteDataSource(
            rubbishApi = com.lambdadigamma.rubbish.source.DefaultRubbishApiService.getRubbishService(),
            ioDispatcher = Dispatchers.IO
        )
    )

    val viewModel = OnboardingRubbishViewModel(rubbishRepository)
    viewModel.load()

    OnboardingHost(
        shouldScroll = false,
        content = {

            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

//                Image(
//                    painter = painterResource(id = R.drawable.onboarding_location),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth()
//                )

//                Spacer(modifier = Modifier.height(100.dp))

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "Wähle eine Straße aus",
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Die App kann Dir den Abfallkalender für Deine Straße anzeigen. Wähle dazu aus der Liste die Straße aus und fahre fort.",
                        textAlign = TextAlign.Left,
                    )

                    Text(text = "Ausgewählte Straße".uppercase())

//                    Card(
//                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//
//                        Text("Adlerstraße", modifier = Modifier.padding(16.dp))
//
//                    }

                    var email by remember { mutableStateOf("") }
                    val autofillNode = AutofillNode(
                        autofillTypes = listOf(
                            AutofillType.PostalAddress,
                            AutofillType.AddressCountry,
                            AutofillType.AddressRegion,
                            AutofillType.AddressStreet
                        ),
                        onFill = { email = it }
                    )
                    val autofill = LocalAutofill.current

                    LocalAutofillTree.current += autofillNode

                    OutlinedTextField(
                        modifier = Modifier
                            .onGloballyPositioned {
                                autofillNode.boundingBox = it.boundsInWindow()
                            }
                            .onFocusChanged { focusState ->
                                autofill?.run {
                                    if (focusState.isFocused) {
                                        requestAutofillForNode(autofillNode)
                                    } else {
                                        cancelAutofillForNode(autofillNode)
                                    }
                                }
                            },
                        value = email,
                        onValueChange = { email = it }
                    )

                    LazyColumn() {

                        items(viewModel.uiState.rubbishStreets) { street ->
                            Text(street.streetName, fontWeight = FontWeight.Medium)
                            street.addition?.let {
                                Text(it, color = MaterialTheme.colorScheme.secondary)
                            }
                        }

                    }


                }

            }

        }
    ) {
        OnboardingRubbishStreetAction()
    }

}

@Composable
fun OnboardingRubbishStreetAction() {
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

@Preview
@Composable
fun OnboardingRubbishStreetScreen_Preview() {
    MeinMoersTheme {
        LegacyMeinMoersTheme {
            OnboardingRubbishStreetScreen(onContinue = {})
        }
    }
}