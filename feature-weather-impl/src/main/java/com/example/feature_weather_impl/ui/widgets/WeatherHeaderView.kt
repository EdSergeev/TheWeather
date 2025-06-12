package com.example.feature_weather_impl.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_data.Data

@Composable
fun WeatherHeaderView(city: Data<String>, modifier: Modifier = Modifier, onEditClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(32.dp),
            tint = Color(0xFF2196F3)
        )
        Spacer(modifier = Modifier.width(8.dp))

        val content = city.content
        when {
            !content.isNullOrEmpty() -> Text(
                text = content,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                fontSize = 20.sp,
                maxLines = 1
            )

            city.isLoading -> Text(
                text = "Loading",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                fontSize = 20.sp,
                maxLines = 1
            )

            else -> Text(
                text = "Error",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                fontSize = 20.sp,
                maxLines = 1
            )
        }

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
                tint = Color(0xFF757575)
            )
        }
    }
}