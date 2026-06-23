package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.AccentCyan
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

/**
 * Container base arredondado com fundo SurfaceCard — usado em previsão, busca e ajustes.
 */
@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .background(SurfaceCard)
            .padding(WeatherTheme.spacing.md),
        content = content,
    )
}

/**
 * Label de seção em maiúsculas com cor cyan — "PRÓXIMOS DIAS", "PREFERÊNCIAS".
 */
@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
        color = AccentCyan,
        modifier = modifier,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1230)
@Composable
private fun WeatherCardPreview() {
    WeatherFeedTheme {
        WeatherCard(modifier = Modifier.fillMaxWidth()) {
            SectionLabel("Próximos Dias")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            ) {
                Text("Hoje", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                Text("25°", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            }
        }
    }
}
