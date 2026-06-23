package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * City search result row with a pin icon, city name and country.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherCityRowView
 *     android:id="@+id/city_row"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * cityRow.bind("São Paulo", "Brasil")
 * cityRow.setOnClickListener { openCity("São Paulo") }
 * ```
 */
class WeatherCityRowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvCityName: TextView
    private val tvCountry: TextView

    init {
        inflate(context, R.layout.weather_city_row, this)
        tvCityName = findViewById(R.id.tv_city_name)
        tvCountry  = findViewById(R.id.tv_country)
    }

    fun bind(cityName: String, country: String) {
        tvCityName.text = cityName
        tvCountry.text  = country
    }
}
