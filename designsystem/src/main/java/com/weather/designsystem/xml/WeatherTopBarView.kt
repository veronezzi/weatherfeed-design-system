package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Top bar showing location text and a search button.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherTopBarView
 *     android:id="@+id/top_bar"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * topBar.setLocation("São Paulo, Brasil")
 * topBar.setOnSearchClick { startSearchActivity() }
 * ```
 */
class WeatherTopBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvLocation: TextView
    private val btnSearch: FrameLayout

    init {
        inflate(context, R.layout.weather_top_bar, this)
        tvLocation = findViewById(R.id.tv_location)
        btnSearch  = findViewById(R.id.btn_search)
    }

    fun setLocation(location: String) {
        tvLocation.text = location
    }

    fun setOnSearchClick(listener: () -> Unit) {
        btnSearch.setOnClickListener { listener() }
    }
}
