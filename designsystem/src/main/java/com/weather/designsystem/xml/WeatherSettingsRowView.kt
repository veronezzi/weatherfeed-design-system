package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.weather.designsystem.R

/**
 * Settings row with a line icon, title, subtitle and an optional trailing view.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherSettingsRowView
 *     android:id="@+id/settings_row"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * settingsRow.setIcon(R.drawable.ic_weather_thermometer)
 * settingsRow.setTitle("Unidade de temperatura")
 * settingsRow.setSubtitle("Celsius ou Fahrenheit")
 *
 * // attach any view as trailing (e.g. WeatherTemperatureToggleView)
 * val toggle = WeatherTemperatureToggleView(context)
 * settingsRow.setTrailing(toggle)
 * ```
 */
class WeatherSettingsRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val ivIcon: ImageView
    private val tvTitle: TextView
    private val tvSubtitle: TextView
    private val flTrailing: FrameLayout

    init {
        inflate(context, R.layout.weather_settings_row, this)
        ivIcon     = findViewById(R.id.iv_settings_icon)
        tvTitle    = findViewById(R.id.tv_settings_title)
        tvSubtitle = findViewById(R.id.tv_settings_subtitle)
        flTrailing = findViewById(R.id.fl_trailing)
    }

    fun setIcon(@DrawableRes icon: Int) {
        ivIcon.setImageResource(icon)
    }

    @Deprecated(
        "Ícones agora são vector drawables do design system; o parâmetro String é ignorado. " +
            "Use setIcon(iconRes).",
    )
    fun setIcon(icon: String) {
        // emoji legado ignorado; o ícone padrão do layout permanece
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setSubtitle(subtitle: String) {
        tvSubtitle.text = subtitle
    }

    fun setTrailing(view: View) {
        flTrailing.removeAllViews()
        flTrailing.addView(view)
    }
}
