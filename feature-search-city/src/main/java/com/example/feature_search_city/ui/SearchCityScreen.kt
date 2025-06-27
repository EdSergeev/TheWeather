package com.example.feature_search_city.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_search_city.R
import com.example.feature_search_city.ui.widgets.SearchCityContentView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchCityScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val viewModel = koinViewModel<SearchCityViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val viewCreatedScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val goBack: () -> Unit = {
        focusManager.clearFocus()
        onBackClick()
    }

    LaunchedEffect(viewModel) {
        viewModel.onCreated(viewCreatedScope)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.large)
        ) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            TextField(
                value = uiState.query,
                onValueChange = { viewModel.onQueryChange(it) },
                placeholder = {
                    Text(stringResource(R.string.search_city_placeholder))
                },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        SearchCityContentView(
            cities = uiState.cities,
            showEmptyResult = uiState.showEmptyResult,
            modifier = Modifier.weight(1f),
        ) { city ->
            viewModel.onCityClicked(city)
            goBack()
        }
    }
}




