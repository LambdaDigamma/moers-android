package com.lambdadigamma.moers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
//    background = black,
//    primary = yellow500,
////    primaryVariant = yellow500,
//    secondary = gray900,
//    onPrimary = black,
//    onBackground = white
)

private val LightColorPalette = lightColorScheme(
    background = white,

    primary = black,
//    primaryVariant = black,
    secondary = gray500,
//    onPrimary = white,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MeinMoersTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    var colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        colors = if (darkTheme) {
//            dynamicDarkColorScheme(LocalContext.current)
//        } else {
//            dynamicLightColorScheme(LocalContext.current)
//        }
//    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}