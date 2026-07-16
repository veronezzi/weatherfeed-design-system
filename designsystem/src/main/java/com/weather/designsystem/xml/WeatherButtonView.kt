package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Pill-shaped button following the design system language
 * (translucent white pill, bold white label).
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherButtonView
 *     android:id="@+id/btn_retry"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:text="Tentar novamente" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * btnRetry.setOnClickListener { viewModel.retry() }
 * ```
 */
class WeatherButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : TextView(context, attrs, defStyleAttr) {

    init {
        background = resources.getDrawable(R.drawable.bg_weather_button_pill, context.theme)
        setTextColor(resources.getColor(R.color.weather_text_primary, context.theme))
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.weather_text_body_large),
        )
        typeface = android.graphics.Typeface.DEFAULT_BOLD
        gravity = android.view.Gravity.CENTER
        isClickable = true
        isFocusable = true

        val h = resources.getDimensionPixelSize(R.dimen.weather_spacing_lg)
        val v = resources.getDimensionPixelSize(R.dimen.weather_spacing_sm) +
            resources.getDimensionPixelSize(R.dimen.weather_spacing_xs)
        setPadding(h, v, h, v)
    }
}
