package com.lambdadigamma.moers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.lambdadigamma.moers.news.RssItemDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsWebDetailScreen(id: Int, onBack: () -> Unit) {

    val viewModel: RssItemDetailViewModel = hiltViewModel()
    viewModel.configure(id.toLong())
    val item by viewModel.item.observeAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            item?.link?.let { link ->
                val state = rememberWebViewState(url = link)
                WebView(
                    state
                )
            }
        }
    }

}