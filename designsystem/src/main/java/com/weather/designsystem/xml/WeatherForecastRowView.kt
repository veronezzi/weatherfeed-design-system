package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Single row for the 5-day forecast screen.
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
 *     conditionIcon  = "☀️",
 *     conditionLabel = "Ensolarado",
 *     tempMax        = "28°",
 *     tempMin        = "19°",
 * )
 * ```
 */
class WeatherForecastRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvDayName: TextView
    private val tvDayDate: TextView
    private val tvConditionIcon: TextView
    private val tvConditionLabel: TextView
    private val tvTempMax: TextView
    private val tvTempMin: TextView

    init {
        inflate(context, R.layout.weather_forecast_row, this)
        tvDayName       = findViewById(R.id.tv_day_name)
        tvDayDate       = findViewById(R.id.tv_day_date)
        tvConditionIcon = findViewById(R.id.tv_condition_icon)
        tvConditionLabel = findViewById(R.id.tv_condition_label)
        tvTempMax       = findViewById(R.id.tv_temp_max)
        tvTempMin       = findViewById(R.id.tv_temp_min)
    }

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
        tvConditionIcon.text  = conditionIcon
        tvConditionLabel.text = conditionLabel
        tvTempMax.text        = tempMax
        tvTempMin.text        = tempMin
    }
}
