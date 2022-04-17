package com.lambdadigamma.moers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LegacyLightColorPalette = lightColors(

)

private val LegacyDarkColorPalette = darkColors(

)

@Composable
fun LegacyMeinMoersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

//    val legacyColors = if (darkTheme) {
//        LegacyDarkColorPalette
//    } else {
//        LegacyLightColorPalette
//    }

    androidx.compose.material.MaterialTheme(
//        colors = legacyColors,
//        typography = Typography,
        content = content
    )
}