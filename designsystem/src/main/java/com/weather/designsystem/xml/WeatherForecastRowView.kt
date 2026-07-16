package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.weather.designsystem.R

/**
 * Single row for the 5-day forecast screen: day pill on the left,
 * condition line icon + label in the middle, max/min temps on the right.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherForecastRowView
 *     android:id="@+id/forecast_row"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * forecastRow.bind(
 *     dayName        = "Terça",
 *     date           = "23 Jun",
 *     conditionIcon  = R.drawable.ic_condition_sun,
 *     conditionLabel = "Ensolarado",
 *     tempMax        = "28°",
 *     tempMin        = "19°",
 * )
 * // or map straight from the OpenWeather code:
 * // conditionIcon = WeatherConditionIcons.fromOpenWeather("01d")
 * ```
 */
class WeatherForecastRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvDayName: TextView
    private val tvDayDate: TextView
    private val ivConditionIcon: ImageView
    private val tvConditionLabel: TextView
    private val tvTempMax: TextView
    private val tvTempMin: TextView

    init {
        inflate(context, R.layout.weather_forecast_row, this)
        tvDayName        = findViewById(R.id.tv_day_name)
        tvDayDate        = findViewById(R.id.tv_day_date)
        ivConditionIcon  = findViewById(R.id.iv_condition_icon)
        tvConditionLabel = findViewById(R.id.tv_condition_label)
        tvTempMax        = findViewById(R.id.tv_temp_max)
        tvTempMin        = findViewById(R.id.tv_temp_min)
    }

    fun bind(
        dayName: String,
        date: String,
        @DrawableRes conditionIcon: Int,
        conditionLabel: String,
        tempMax: String,
        tempMin: String,
    ) {
        tvDayName.text        = dayName
        tvDayDate.text        = date
        ivConditionIcon.setImageResource(conditionIcon)
        tvConditionLabel.text = conditionLabel
        tvTempMax.text        = tempMax
        tvTempMin.text        = tempMin
    }

    @Deprecated(
        "Ícones agora são vector drawables do design system; o parâmetro String é ignorado. " +
            "Use a sobrecarga com @DrawableRes (ex.: WeatherConditionIcons.fromOpenWeather(code)).",
    )
    fun bind(
        dayName: String,
        date: String,
        conditionIcon: String,
        conditionLabel: String,
        tempMax: String,
        tempMin: String,
    ) {
        tvDayName.text        = dayName
        tvDayDate.text        = date
        tvConditionLabel.text = conditionLabel
        tvTempMax.text        = tempMax
        tvTempMin.text        = tempMin
    }
}
