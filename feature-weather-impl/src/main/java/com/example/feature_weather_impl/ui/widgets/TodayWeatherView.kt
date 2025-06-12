package com.example.feature_weather_impl.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.Black05
import com.example.core_ui.theme.TheWeatherTheme
import com.example.feature_weather_api.models.Weather
import com.example.feature_weather_impl.utils.TimeUtils
import com.example.feature_weather_impl.utils.WeatherUtils

@Composable
internal fun TodayWeatherView(hours: List<Weather>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Black05, shape = MaterialTheme.shapes.large),
    ) {
        items(hours) { hour ->
            HourItem(hour)
        }
    }
}

@Composable
private fun HourItem(hour: Weather) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = TimeUtils.formatTimestampToLocalizedHour(hour.timestamp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Image(
            painter = painterResource(
                WeatherUtils.getWeatherIconResource(
                    context = LocalContext.current,
                    iconCode = hour.iconId,
                )
            ),
            contentDescription = "Weather",
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = WeatherUtils.formatTemperature(hour.temperature),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTodayWeatherView() {
    val hour = Weather(
        temperature = 19.5f,
        iconId = "10d",
        timestamp = 0
    )

    TheWeatherTheme {
        Surface {
            TodayWeatherView(
                List(24) { hour }
            )
        }
    }
}