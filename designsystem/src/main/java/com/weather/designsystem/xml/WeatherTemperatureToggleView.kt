package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Segmented °C / °F toggle.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherTemperatureToggleView
 *     android:id="@+id/temp_toggle"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * tempToggle.setUnit(isCelsius = true)
 * tempToggle.setOnUnitChanged { isCelsius -> viewModel.setUnit(isCelsius) }
 * ```
 */
class WeatherTemperatureToggleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvCelsius: TextView
    private val tvFahrenheit: TextView

    private var isCelsius: Boolean = true
    private var onUnitChanged: ((Boolean) -> Unit)? = null

    private val activeBackground  by lazy { resources.getDrawable(R.drawable.bg_weather_toggle_active, context.theme) }
    private val inactiveTextColor by lazy { resources.getColor(R.color.weather_text_secondary, context.theme) }
    private val activeTextColor   by lazy { resources.getColor(R.color.weather_background_deep, context.theme) }

    init {
        inflate(context, R.layout.weather_temperature_toggle, this)
        tvCelsius    = findViewById(R.id.tv_celsius)
        tvFahrenheit = findViewById(R.id.tv_fahrenheit)

        tvCelsius.setOnClickListener    { select(true) }
        tvFahrenheit.setOnClickListener { select(false) }
        renderState()
    }

    fun setUnit(isCelsius: Boolean) {
        this.isCelsius = isCelsius
        renderState()
    }

    fun getUnit(): Boolean = isCelsius

    fun setOnUnitChanged(listener: (Boolean) -> Unit) {
        onUnitChanged = listener
    }

    private fun select(celsius: Boolean) {
        if (isCelsius == celsius) return
        isCelsius = celsius
        renderState()
        onUnitChanged?.invoke(isCelsius)
    }

    private fun renderState() {
        if (isCelsius) {
            tvCelsius.background    = activeBackground
            tvCelsius.setTextColor(activeTextColor)
            tvFahrenheit.background = null
            tvFahrenheit.setTextColor(inactiveTextColor)
        } else {
            tvFahrenheit.background = activeBackground
            tvFahrenheit.setTextColor(activeTextColor)
            tvCelsius.background    = null
            tvCelsius.setTextColor(inactiveTextColor)
        }
    }
}
