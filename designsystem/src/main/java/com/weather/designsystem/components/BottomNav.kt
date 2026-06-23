package com.weather.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weather.designsystem.theme.AccentBlue
import com.weather.designsystem.theme.BackgroundDeep
import com.weather.designsystem.theme.NavActivePill
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherFeedTheme
import com.weather.designsystem.theme.WeatherTheme

data class NavItem(
    val icon: String,
    val label: String,
)

/**
 * Barra de navegação inferior com 4 abas — Clima, 5 Dias, Buscar, Ajustes.
 * Item ativo tem círculo de fundo azul/navy.
 */
@Composable
fun WeatherBottomNav(
    items: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundDeep)
            .padding(vertical = WeatherTheme.spacing.sm),
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .clickable(
                        onClick = { onItemSelected(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    .padding(horizontal = WeatherTheme.spacing.sm),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) NavActivePill else Color.Transparent),
                ) {
                    Text(item.icon, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
                }
                Text(
                    text = item.label,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = if (isSelected) AccentBlue else TextSecondary,
                )
            }
        }
    }
}

val defaultNavItems = listOf(
    NavItem("🌤", "Clima"),
    NavItem("📅", "5 Dias"),
    NavItem("🔍", "Buscar"),
    NavItem("⚙️", "Ajustes"),
)

@Preview(showBackground = true, backgroundColor = 0xFF0A0F28)
@Composable
private fun BottomNavPreview() {
    WeatherFeedTheme {
        var selected by remember { mutableIntStateOf(0) }
        WeatherBottomNav(
            items = defaultNavItems,
            selectedIndex = selected,
            onItemSelected = { selected = it },
        )
    }
}
