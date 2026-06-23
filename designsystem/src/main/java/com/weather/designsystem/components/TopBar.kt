package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.Overlay
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

/**
 * Barra superior da tela principal — localização à esquerda, ícone de busca à direita.
 */
@Composable
fun WeatherTopBar(
    locationText: String,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WeatherTheme.spacing.md, vertical = WeatherTheme.spacing.md),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(WeatherTheme.spacing.xs),
        ) {
            Text("📍", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(
                text = locationText,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
        }

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Overlay, CircleShape),
        ) {
            Text("🔍", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF3B4BC8)
@Composable
private fun TopBarPreview() {
    WeatherFeedTheme {
        WeatherTopBar(locationText = "São Paulo, Brasil", onSearchClick = {})
    }
}
