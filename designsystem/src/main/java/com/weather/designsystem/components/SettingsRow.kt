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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.NavActivePill
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

/**
 * Linha de configuração com ícone, título, subtítulo e conteúdo de trailing (ex: toggle °C/°F).
 */
@Composable
fun SettingsRow(
    icon: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    trailing: @Composable () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(WeatherTheme.spacing.md),
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .background(SurfaceCard)
            .padding(WeatherTheme.spacing.md),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(NavActivePill),
        ) {
            Text(icon, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = androidx.compose.material3.MaterialTheme.typography.titleMedium, color = TextPrimary)
            Text(subtitle, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextSecondary)
        }

        trailing()
    }
}

/**
 * Toggle segmentado °C / °F — usado na tela de Ajustes.
 */
@Composable
fun TemperatureToggle(
    isCelsius: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.pill))
            .background(NavActivePill)
            .padding(3.dp),
    ) {
        listOf(true to "°C", false to "°F").forEach { (celsius, label) ->
            val isSelected = (isCelsius == celsius)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(WeatherTheme.radius.pill))
                    .background(
                        if (isSelected) com.weather.designsystem.theme.AccentBlue
                        else androidx.compose.ui.graphics.Color.Transparent
                    )
                    .then(
                        Modifier.clickable(
                            onClick = { onToggle(celsius) },
                            indication = null,
                            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Text(
                    text = label,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = if (isSelected) com.weather.designsystem.theme.BackgroundDeep
                    else TextSecondary,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1230)
@Composable
private fun SettingsRowPreview() {
    WeatherFeedTheme {
        var celsius by remember { mutableStateOf(true) }
        SettingsRow(
            icon = "🌡",
            title = "Unidade de temperatura",
            subtitle = "Celsius ou Fahrenheit",
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            trailing = { TemperatureToggle(celsius, { celsius = it }) },
        )
    }
}
