package com.example.feature_weather_impl.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
internal fun WeekWeatherView(days: List<Weather>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Black05, shape = MaterialTheme.shapes.large),
    ) {
        items(days) { day ->
            DayItem(day)
        }
    }
}

@Composable
private fun DayItem(day: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = TimeUtils.formatTimestampToLocalizedWeekday(day.timestamp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
        )
        Image(
            painter = painterResource(
                WeatherUtils.getWeatherIconResource(
                    context = LocalContext.current,
                    iconCode = day.iconId
                )
            ),
            contentDescription = "Weather",
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = WeatherUtils.formatTemperature(day.temperature),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeekWeatherView() {
    val day = Weather(
        temperature = 19.5f,
        iconId = "10d",
        timestamp = 0
    )

    TheWeatherTheme {
        Surface() {
            WeekWeatherView(List(7) { day })
        }
    }
}