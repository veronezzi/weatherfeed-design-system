package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.Divider
import com.weather.designsystem.theme.Overlay
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

data class WeatherStat(
    val icon: String,   // emoji ou texto do ícone
    val label: String,
    val value: String,
)

/**
 * Card horizontal com 3 métricas de clima: Sensação, Umidade, Vento.
 */
@Composable
fun StatCard(
    stats: List<WeatherStat>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .background(Overlay)
            .padding(vertical = WeatherTheme.spacing.md),
    ) {
        stats.forEachIndexed { index, stat ->
            StatItem(stat, modifier = Modifier.weight(1f))
            if (index < stats.size - 1) {
                VerticalDivider(
                    thickness = 0.5.dp,
                    color = Divider,
                    modifier = Modifier.width(1.dp).padding(vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
private fun StatItem(stat: WeatherStat, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = stat.icon,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stat.label,
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )
        Text(
            text = stat.value,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            color = TextPrimary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF3B4BC8)
@Composable
private fun StatCardPreview() {
    WeatherFeedTheme {
        StatCard(
            stats = listOf(
                WeatherStat("🌡", "Sensação", "26°"),
                WeatherStat("💧", "Umidade", "68%"),
                WeatherStat("💨", "Vento", "12 km/h"),
            ),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
    }
}
