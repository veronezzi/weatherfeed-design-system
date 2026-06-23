package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.AccentBlue
import com.weather.designsystem.theme.NavActivePill
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

/**
 * Item de resultado de busca de cidade.
 */
@Composable
fun CityRow(
    cityName: String,
    country: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(WeatherTheme.spacing.md),
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .background(SurfaceCard)
            .clickable(onClick = onClick)
            .padding(WeatherTheme.spacing.md),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(NavActivePill),
        ) {
            Text("📍", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }

        Column {
            Text(
                text = cityName,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
            Text(
                text = country,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1230)
@Composable
private fun CityRowPreview() {
    WeatherFeedTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            CityRow("São Paulo", "Brasil", onClick = {}, modifier = Modifier.fillMaxWidth())
            CityRow("Lisboa", "Portugal", onClick = {}, modifier = Modifier.fillMaxWidth())
        }
    }
}
