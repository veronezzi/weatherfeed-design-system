package com.weather.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val WeatherColorScheme = darkColorScheme(
    primary          = AccentBlue,
    onPrimary        = BackgroundDeep,
    primaryContainer = NavActivePill,
    secondary        = AccentOrange,
    onSecondary      = TextPrimary,
    background       = BackgroundDark,
    onBackground     = TextPrimary,
    surface          = SurfaceDark,
    onSurface        = TextPrimary,
    surfaceVariant   = SurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline          = Divider,
)

@Composable
fun WeatherFeedTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalWeatherSpacing provides WeatherSpacing(),
        LocalWeatherRadius  provides WeatherRadius(),
    ) {
        MaterialTheme(
            colorScheme = WeatherColorScheme,
            typography  = WeatherTypography,
            content     = content,
        )
    }
}

object WeatherTheme {
    val spacing: WeatherSpacing
        @Composable get() = LocalWeatherSpacing.current
    val radius: WeatherRadius
        @Composable get() = LocalWeatherRadius.current
}
