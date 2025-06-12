package com.example.feature_weather_impl.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.core_data.Data
import com.example.core_ui.theme.TheWeatherTheme
import com.example.feature_weather_impl.R
import com.valentinilk.shimmer.shimmer

@Composable
internal fun WeatherHeaderView(city: Data<String>, modifier: Modifier = Modifier, onEditClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // location icon
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))

        // city
        val content = city.content
        when {
            !content.isNullOrEmpty() -> LocationText(content)

            city.isLoading -> Box(modifier = Modifier.weight(1f)) {
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

            else -> LocationText(stringResource(R.string.error_unknown_city))
        }

        // edit icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onEditClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RowScope.LocationText(city: String) {
    Text(
        text = city,
        modifier = Modifier.weight(1f),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 1
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeatherHeaderViewSuccess() {
    TheWeatherTheme {
        Surface {
            WeatherHeaderView(Data.success("London")) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeatherHeaderViewLoading() {
    TheWeatherTheme {
        Surface(Modifier.fillMaxWidth()) {
            WeatherHeaderView(Data.loading()) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeatherHeaderViewError() {
    TheWeatherTheme {
        Surface(Modifier.fillMaxWidth()) {
            WeatherHeaderView(Data.error(Exception())) {}
        }
    }
}