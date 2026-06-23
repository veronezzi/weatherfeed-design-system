package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.AccentBlue
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

/**
 * Barra de busca arredondada pill — tela Buscar.
 */
@Composable
fun WeatherSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar cidade ou país...",
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
        cursorBrush = SolidColor(AccentBlue),
        singleLine = true,
        modifier = modifier,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(52.dp)
                    .clip(RoundedCornerShape(WeatherTheme.radius.pill))
                    .background(SurfaceCard)
                    .padding(horizontal = WeatherTheme.spacing.md),
            ) {
                Text(
                    text = "🔍",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                )
                Spacer(Modifier.width(WeatherTheme.spacing.sm))
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0D1230)
@Composable
private fun SearchBarPreview() {
    WeatherFeedTheme {
        WeatherSearchBar(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}
