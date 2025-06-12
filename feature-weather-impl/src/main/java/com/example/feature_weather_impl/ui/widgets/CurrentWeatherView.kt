package com.example.feature_weather_impl.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.Black05
import com.example.core_ui.theme.TheWeatherTheme
import com.example.feature_weather_api.models.Weather
import com.example.feature_weather_impl.utils.WeatherUtils
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CurrentWeatherView(weather: Weather, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Black05, shape = MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = WeatherUtils.formatTemperature(weather.temperature),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        val iconId = WeatherUtils.getWeatherIconResource(LocalContext.current, weather.iconId)
        if (iconId > 0) {
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(iconId),
                contentDescription = "Weather",
                modifier = Modifier.size(64.dp),
            )
        }
    }
}

@Composable
internal fun LoadingCurrentWeatherView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Black05, shape = MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .shimmer()
                .width(128.dp)
                .height(30.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCurrentWeatherView() {
    val weather = Weather(
        temperature = 19.5f,
        iconId = "10d",
        timestamp = 0
    )
    TheWeatherTheme {
        Surface {
            CurrentWeatherView(weather)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingCurrentWeatherView() {
    TheWeatherTheme {
        Surface {
            LoadingCurrentWeatherView()
        }
    }
}
