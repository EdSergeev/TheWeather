package com.example.feature_search_city.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.model.UiData
import com.example.core_ui.theme.TheWeatherTheme
import com.example.feature_search_city.R
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import com.valentinilk.shimmer.shimmer

@Composable
internal fun SearchCityContentView(
    cities: UiData<List<LocationDesc>>,
    showEmptyResult: Boolean,
    modifier: Modifier = Modifier,
    onCityClick: (LocationDesc) -> Unit
) {

    val content = cities.content
    when {
        !content.isNullOrEmpty() -> {
            LazyColumn(modifier) {
                items(content) { city ->
                    CityItem(location = city) {
                        onCityClick.invoke(city)
                    }
                }
            }
        }

        cities.isLoading -> LazyColumn(modifier) {
            items(5) {
                LoadingCityItem()
            }
        }

        showEmptyResult -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.search_city_empty_result),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun CityItem(location: LocationDesc, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = location.city,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        val state = location.state
        if (!state.isNullOrEmpty()) {
            Text(
                text = " • $state",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun LoadingCityItem() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(48.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .shimmer()
                .width(128.dp)
                .height(24.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCityItem() {
    TheWeatherTheme {
        Surface {
            CityItem(LocationDesc("London", "Kentuki", Location(0.0, 0.0))) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingCityItem() {
    TheWeatherTheme {
        Surface {
            LoadingCityItem()
        }
    }
}