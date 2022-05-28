package com.lambdadigamma.moers.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.onboarding.ui.OnboardingHost
import com.lambdadigamma.moers.ui.theme.LegacyMeinMoersTheme
import com.lambdadigamma.moers.ui.theme.MeinMoersTheme
import com.lambdadigamma.rubbish.RubbishRepository
import com.lambdadigamma.rubbish.source.DefaultRubbishApiService
import com.lambdadigamma.rubbish.source.RubbishRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingRubbishStreetScreen(
    onContinue: () -> Unit
) {

    val context = LocalContext.current

    val rubbishRepository = RubbishRepository(
        context = context,
        remoteDataSource = RubbishRemoteDataSource(
            rubbishApi = DefaultRubbishApiService.getRubbishService(),
            ioDispatcher = Dispatchers.IO
        )
    )

    val viewModel = OnboardingRubbishViewModel(rubbishRepository)

    OnboardingHost(
        shouldScroll = false,
        content = {

            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            ) {

                TopRubbishStreetSelection(viewModel = viewModel)

                Divider()

                val focusManager = LocalFocusManager.current

                Surface(tonalElevation = 1.dp) {
                    LazyColumn(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {

                        items(viewModel.uiState.rubbishStreets) { street ->

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shadowElevation = 1.dp,
                                tonalElevation = 0.dp,
                                color = MaterialTheme.colorScheme.background,
                                onClick = {
                                    viewModel.selectStreet(street)
                                    focusManager.clearFocus()
                                    onContinue()
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 16.dp,
                                            horizontal = 16.dp
                                        )
                                ) {
                                    Text(street.streetName, fontWeight = FontWeight.Medium)
                                    street.addition?.let {
                                        if (it.isNotEmpty()) {
                                            Text(it, color = MaterialTheme.colorScheme.secondary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        OnboardingRubbishStreetAction(action = {
            onContinue()
        })
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopRubbishStreetSelection(viewModel: OnboardingRubbishViewModel) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(R.string.onboarding_rubbish_street_selection_title),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = stringResource(R.string.onboarding_rubbish_street_selection_body),
            textAlign = TextAlign.Left,
        )

        Spacer(modifier = Modifier.height(16.dp))

        val scope = rememberCoroutineScope()
        val searchTerm = viewModel.searchTerm.collectAsState()

//        var searchTerm by remember { mutableStateOf(viewModel.uiState.searchTerm) }
        val autofillNode = AutofillNode(
            autofillTypes = listOf(
//                AutofillType.PostalAddress,
//                AutofillType.AddressCountry,
//                AutofillType.AddressRegion,
                AutofillType.AddressStreet
            ),
            onFill = { viewModel.updateSearchTerm(it) }
        )

        val focusManager = LocalFocusManager.current
        val autofill = LocalAutofill.current

        LocalAutofillTree.current += autofillNode

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .weight(2f)
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
                value = searchTerm.value,
                label = {
                    Text(stringResource(R.string.onboarding_rubbish_street_placeholder))
                },
                onValueChange = { searchTerm ->
                    viewModel.updateSearchTerm(searchTerm)
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                    }
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {
                scope.launch {
                    viewModel.loadLocation()
                }
            }) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = "Search current location",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }
}

@Composable
fun OnboardingRubbishStreetAction(action: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = { action() },
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(
                text = stringResource(R.string.onboarding_rubbish_street_skip_action),
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