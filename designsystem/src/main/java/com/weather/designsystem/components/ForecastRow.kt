package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.AccentBlue
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

data class ForecastDay(
    val dayName: String,
    val date: String,
    val conditionIcon: String,
    val conditionLabel: String,
    val tempMax: String,
    val tempMin: String,
)

/**
 * Linha de previsão de um dia — exibida na tela "5 Dias".
 */
@Composable
fun ForecastRow(
    day: ForecastDay,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .background(SurfaceCard)
            .padding(horizontal = WeatherTheme.spacing.md, vertical = WeatherTheme.spacing.md),
    ) {
        // Dia + data
        Column(modifier = Modifier.weight(1.2f)) {
            Text(
                text = day.dayName,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
            Text(
                text = day.date,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
        }

        // Ícone da condição
        Text(
            text = day.conditionIcon,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
            ),
            modifier = Modifier
                .size(32.dp)
                .weight(0.6f),
        )

        // Descrição
        Text(
            text = day.conditionLabel,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.weight(1.5f),
        )

        // Temp máx / mín
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = day.tempMax,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
            Text(
                text = day.tempMin,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextSecondary,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1230)
@Composable
private fun ForecastRowPreview() {
    WeatherFeedTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            ForecastRow(ForecastDay("Hoje", "22 Jun", "⛅", "Parc. nublado", "25°", "18°"), Modifier.fillMaxWidth())
            ForecastRow(ForecastDay("Terça", "23 Jun", "☀️", "Ensolarado", "28°", "19°"), Modifier.fillMaxWidth())
            ForecastRow(ForecastDay("Quarta", "24 Jun", "🌧", "Chuvoso", "21°", "16°"), Modifier.fillMaxWidth())
        }
    }
}
