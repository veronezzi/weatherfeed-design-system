package com.weather.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val WeatherTypography = Typography(
    // "24°" — temperatura principal
    displayLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 72.sp,
        lineHeight = 76.sp,
        color = TextPrimary,
    ),
    // Título de tela "Previsão de 5 dias"
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        color = TextPrimary,
    ),
    // Cidade "São Paulo, Brasil"
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        color = TextPrimary,
    ),
    // "Parcialmente nublado"
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = TextPrimary,
    ),
    // "Sensação térmica 26°C"
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = TextSecondary,
    ),
    // Labels de stat (Sensação, Umidade, Vento)
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = TextSecondary,
    ),
    // "PRÓXIMOS DIAS" / "PREFERÊNCIAS"
    labelSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 1.sp,
        color = AccentCyan,
    ),
)
