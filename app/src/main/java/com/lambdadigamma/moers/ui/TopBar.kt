package com.lambdadigamma.moers.ui

import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable

@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = contentColorFor(backgroundColor),
    )
}

