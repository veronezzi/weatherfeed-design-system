package com.weather.designsystem

import androidx.annotation.DrawableRes

/**
 * Maps OpenWeather icon codes (e.g. "01d", "10n") to the design system's
 * line-style condition icons, so apps don't need to load the OpenWeather
 * PNG assets (which don't match the visual language).
 *
 * Usage:
 * ```kotlin
 * val res = WeatherConditionIcons.fromOpenWeather(weather.icon)
 * imageView.setImageResource(res)
 * ```
 */
object WeatherConditionIcons {

    @DrawableRes
    fun fromOpenWeather(code: String): Int {
        val night = code.endsWith("n")
        return when (code.take(2)) {
            "01" -> if (night) R.drawable.ic_condition_moon else R.drawable.ic_condition_sun
            "02" -> if (night) R.drawable.ic_condition_moon else R.drawable.ic_condition_sun_cloud
            "03", "04" -> R.drawable.ic_weather_cloud
            "09", "10" -> R.drawable.ic_condition_rain
            "11" -> R.drawable.ic_condition_storm
            "13" -> R.drawable.ic_condition_snow
            "50" -> R.drawable.ic_condition_mist
            else -> R.drawable.ic_weather_cloud
        }
    }
}
