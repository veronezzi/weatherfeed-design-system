package com.example.weaterdesignsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weather.designsystem.components.CityRow
import com.weather.designsystem.components.ForecastDay
import com.weather.designsystem.components.ForecastRow
import com.weather.designsystem.components.NavItem
import com.weather.designsystem.components.SectionLabel
import com.weather.designsystem.components.SettingsRow
import com.weather.designsystem.components.StatCard
import com.weather.designsystem.components.TemperatureToggle
import com.weather.designsystem.components.WeatherBottomNav
import com.weather.designsystem.components.WeatherCard
import com.weather.designsystem.components.WeatherSearchBar
import com.weather.designsystem.components.WeatherStat
import com.weather.designsystem.components.WeatherTopBar
import com.weather.designsystem.theme.AccentCyan
import com.weather.designsystem.theme.AccentOrange
import com.weather.designsystem.theme.BackgroundDark
import com.weather.designsystem.theme.BackgroundDeep
import com.weather.designsystem.theme.GradientEnd
import com.weather.designsystem.theme.GradientStart
import com.weather.designsystem.theme.NavActivePill
import com.weather.designsystem.theme.SurfaceCard
import com.weather.designsystem.theme.SurfaceElevated
import com.weather.designsystem.theme.TextPrimary
import com.weather.designsystem.theme.TextSecondary
import com.weather.designsystem.theme.WeatherTheme

data class ComponentEntry(
    val name: String,
    val category: String,
    val description: String,
)

private val allComponents = listOf(
    ComponentEntry("WeatherTopBar", "Navigation", "Localização + botão de busca — tela principal"),
    ComponentEntry("WeatherBottomNav", "Navigation", "4 abas: Clima, 5 Dias, Buscar, Ajustes"),
    ComponentEntry("WeatherCard", "Containers", "Container arredondado com fundo SurfaceCard"),
    ComponentEntry("SectionLabel", "Containers", "Label uppercase em cyan — PRÓXIMOS DIAS"),
    ComponentEntry("StatCard", "Weather", "Card horizontal: Sensação / Umidade / Vento"),
    ComponentEntry("ForecastRow", "Weather", "Linha de previsão de um dia"),
    ComponentEntry("WeatherSearchBar", "Inputs", "Barra de busca pill — tela Buscar"),
    ComponentEntry("CityRow", "Inputs", "Item de resultado de cidade com pin"),
    ComponentEntry("SettingsRow", "Settings", "Linha de configuração com ícone e trailing"),
    ComponentEntry("TemperatureToggle", "Settings", "Seletor °C / °F"),
    ComponentEntry("WeatherFeedTheme", "Theme", "Tema completo: cores, tipografia, espaçamento"),
    ComponentEntry("GradientStart", "Tokens · Colors", "#3B4BC8 — início do gradiente home"),
    ComponentEntry("GradientEnd", "Tokens · Colors", "#7B2FCA — fim do gradiente home"),
    ComponentEntry("BackgroundDark", "Tokens · Colors", "#0D1230 — fundo das telas secundárias"),
    ComponentEntry("SurfaceCard", "Tokens · Colors", "#1A2040 — fundo de cards e linhas"),
    ComponentEntry("AccentBlue", "Tokens · Colors", "#5B9EF0 — destaque / nav ativo"),
    ComponentEntry("AccentOrange", "Tokens · Colors", "#FF8C42 — título Configurações"),
    ComponentEntry("WeatherSpacing", "Tokens · Spacing", "xs=4, sm=8, md=16, lg=24, xl=32, xxl=48 dp"),
    ComponentEntry("WeatherRadius", "Tokens · Radius", "sm=8, md=16, lg=20, pill=50 dp"),
    ComponentEntry("WeatherTypography", "Tokens · Type", "Display 72sp · Headline 24sp · Title 17sp · Body 16sp"),
)

private val categories = listOf("Todos") + allComponents.map { it.category }.distinct()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatalogScreen() {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    val filtered = allComponents.filter { entry ->
        val matchesQuery = query.isEmpty() ||
            entry.name.contains(query, ignoreCase = true) ||
            entry.description.contains(query, ignoreCase = true)
        val matchesCategory = selectedCategory == "Todos" || entry.category == selectedCategory
        matchesQuery && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundDeep)
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            Column {
                SectionLabel("WeatherFeed")
                Text(
                    text = "Design System",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                        color = AccentOrange,
                    ),
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(WeatherTheme.spacing.sm),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = WeatherTheme.spacing.md),
        ) {
            item {
                Spacer(Modifier.height(WeatherTheme.spacing.md))
                WeatherSearchBar(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item {
                Spacer(Modifier.height(WeatherTheme.spacing.sm))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(WeatherTheme.spacing.sm)) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(WeatherTheme.radius.pill))
                                .background(if (isSelected) NavActivePill else SurfaceCard)
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = cat,
                                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                color = if (isSelected) AccentCyan else TextSecondary,
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "${filtered.size} componente(s)",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
            }

            items(filtered, key = { it.name }) { entry ->
                ComponentCard(entry)
            }

            item { Spacer(Modifier.height(WeatherTheme.spacing.lg)) }
        }
    }
}

@Composable
private fun ComponentCard(entry: ComponentEntry) {
    var expanded by remember { mutableStateOf(false) }

    WeatherCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(WeatherTheme.radius.lg))
            .clickable { expanded = !expanded },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = entry.name,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(WeatherTheme.radius.pill))
                    .background(SurfaceElevated)
                    .padding(horizontal = 8.dp, vertical = 3.dp),
            ) {
                Text(
                    text = entry.category,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = AccentCyan,
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = entry.description,
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )

        AnimatedVisibility(visible = expanded) {
            Column {
                Spacer(Modifier.height(WeatherTheme.spacing.md))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(WeatherTheme.radius.md))
                        .background(BackgroundDeep)
                        .padding(WeatherTheme.spacing.md),
                ) {
                    ComponentPreview(entry)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ComponentPreview(entry: ComponentEntry) {
    when (entry.name) {
        "WeatherTopBar" -> WeatherTopBar("São Paulo, Brasil", onSearchClick = {})

        "WeatherBottomNav" -> {
            var sel by remember { mutableIntStateOf(0) }
            WeatherBottomNav(
                items = listOf(NavItem("🌤", "Clima"), NavItem("📅", "5 Dias"), NavItem("🔍", "Buscar"), NavItem("⚙️", "Ajustes")),
                selectedIndex = sel,
                onItemSelected = { sel = it },
            )
        }

        "WeatherCard" -> WeatherCard(modifier = Modifier.fillMaxWidth()) {
            Text("Exemplo de card", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Text("Conteúdo aqui", style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextSecondary)
        }

        "SectionLabel" -> SectionLabel("Próximos Dias")

        "StatCard" -> StatCard(
            stats = listOf(
                WeatherStat("🌡", "Sensação", "26°"),
                WeatherStat("💧", "Umidade", "68%"),
                WeatherStat("💨", "Vento", "12 km/h"),
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        "ForecastRow" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            ForecastRow(ForecastDay("Hoje", "22 Jun", "⛅", "Parc. nublado", "25°", "18°"), Modifier.fillMaxWidth())
            ForecastRow(ForecastDay("Terça", "23 Jun", "☀️", "Ensolarado", "28°", "19°"), Modifier.fillMaxWidth())
            ForecastRow(ForecastDay("Quarta", "24 Jun", "🌧", "Chuvoso", "21°", "16°"), Modifier.fillMaxWidth())
        }

        "WeatherSearchBar" -> {
            var text by remember { mutableStateOf("") }
            WeatherSearchBar(value = text, onValueChange = { text = it }, modifier = Modifier.fillMaxWidth())
        }

        "CityRow" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            CityRow("São Paulo", "Brasil", onClick = {}, modifier = Modifier.fillMaxWidth())
            CityRow("Lisboa", "Portugal", onClick = {}, modifier = Modifier.fillMaxWidth())
        }

        "SettingsRow" -> SettingsRow(
            icon = "🌡",
            title = "Unidade de temperatura",
            subtitle = "Celsius ou Fahrenheit",
            modifier = Modifier.fillMaxWidth(),
            trailing = { TemperatureToggle(true, {}) },
        )

        "TemperatureToggle" -> {
            var celsius by remember { mutableStateOf(true) }
            TemperatureToggle(celsius, { celsius = it })
        }

        "GradientStart" -> Box(Modifier.fillMaxWidth().height(40.dp).background(GradientStart, RoundedCornerShape(8.dp)))
        "GradientEnd" -> Box(Modifier.fillMaxWidth().height(40.dp).background(GradientEnd, RoundedCornerShape(8.dp)))
        "BackgroundDark" -> Box(Modifier.fillMaxWidth().height(40.dp).background(BackgroundDark, RoundedCornerShape(8.dp)))
        "SurfaceCard" -> Box(Modifier.fillMaxWidth().height(40.dp).background(SurfaceCard, RoundedCornerShape(8.dp)))
        "AccentBlue" -> Box(Modifier.fillMaxWidth().height(40.dp).background(com.weather.designsystem.theme.AccentBlue, RoundedCornerShape(8.dp)))
        "AccentOrange" -> Box(Modifier.fillMaxWidth().height(40.dp).background(AccentOrange, RoundedCornerShape(8.dp)))

        "WeatherSpacing" -> Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("xs" to "4dp", "sm" to "8dp", "md" to "16dp", "lg" to "24dp", "xl" to "32dp", "xxl" to "48dp").forEach { (k, v) ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(k, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(v, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextPrimary)
                }
            }
        }

        "WeatherRadius" -> Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("sm" to "8dp", "md" to "16dp", "lg" to "20dp", "pill" to "50dp").forEach { (k, v) ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(k, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(v, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextPrimary)
                }
            }
        }

        "WeatherTypography" -> Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Display 72sp", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Text("Headline 24sp", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Text("Title 17sp", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Text("Body 16sp", style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
            Text("Body small 12sp", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
            SectionLabel("Label uppercase 11sp")
        }

        "WeatherFeedTheme" -> Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SectionLabel("Tema Ativo")
            Text("WeatherFeedTheme envolvendo o app", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }

        else -> Text("Preview não disponível", style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = TextSecondary)
    }
}
