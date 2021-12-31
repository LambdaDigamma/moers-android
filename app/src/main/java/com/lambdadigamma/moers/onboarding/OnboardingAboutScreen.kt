package com.lambdadigamma.moers.onboarding

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lambdadigamma.moers.R
import com.lambdadigamma.moers.onboarding.ui.OnboardingTopBar

@Composable
fun OnboardingAboutScreen(onContinue: () -> Unit) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {

        OnboardingTopBar(text = "Über diese App")

//        Column {
//            OnboardingSteps(modifier = Modifier.padding(16.dp))
//            Divider()
//        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 16.dp),
//                .fillMaxSize()
//                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OpenDataInfoCard()

            ContributeInfoCard()

//            Card(
//                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline),
//                backgroundColor = MaterialTheme.colorScheme.surface,
//                shape = RoundedCornerShape(12.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Die App speichert Deine Einstellungen nur lokal auf dem Gerät.",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//            }

            TermsAndPrivacyInfoCard()

        }

        Column {
            Divider()
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_privacy_continue_button),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Text(
                    text = "Durch Fortfahren stimmst Du den AGBs zu und nimmst die Datenschutzerklärung zur Kenntnis.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}

@Composable
fun OpenDataInfoCard() {

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                stringResource(R.string.onboarding_about_open_data_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                Text(
                    text = stringResource(R.string.onboarding_about_open_data_intro),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = stringResource(R.string.onboarding_about_open_data_text),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.onboarding_about_open_data_end),
                    style = MaterialTheme.typography.bodyMedium,
                )

            }

        }

    }

}

@Composable
fun ContributeInfoCard() {

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                stringResource(R.string.onboarding_about_contribute_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                Text(
                    text = "Die App wird von Lennart Fischer konzeptioniert und entwickelt. Der gesamte Programmcode der App ist auf GitHub zu finden. Jede und jeder ist eingeladen, an der Entwicklung mitzuwirken oder Ideen einzubringen. Alle weiteren Helfer:innen sind in auch in der App zu finden.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Du möchtest auch mithelfen?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Super! Du kannst Deine Ideen oder Dein Wissen einbringen. Weitere Informationen findest Du hier.",
                    style = MaterialTheme.typography.bodyMedium,
                )

            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.onboarding_about_cta))
            }

        }

    }

}

@Composable
fun TermsAndPrivacyInfoCard() {

    Card(
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                stringResource(R.string.onboarding_about_terms_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = stringResource(R.string.onboarding_privacy_card_info),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {

                val context = LocalContext.current
                val termsIntent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://moers.app/legal/tac")
                    )
                }
                val privacyIntent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://moers.app/legal/privacy")
                    )
                }

                OutlinedButton(onClick = {
                    context.startActivity(termsIntent)
                }) {
                    Text(stringResource(R.string.onboarding_privacy_terms))
                }

                OutlinedButton(onClick = {
                    context.startActivity(privacyIntent)
                }) {
                    Text(stringResource(R.string.onboarding_privacy_privacy))
                }

            }
        }
    }

}