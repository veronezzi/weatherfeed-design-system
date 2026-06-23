package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Horizontal card showing 3 weather metrics (e.g. Sensação / Umidade / Vento).
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherStatCardView
 *     android:id="@+id/stat_card"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * statCard.setStat1("🌡", "Sensação", "26°")
 * statCard.setStat2("💧", "Umidade",  "68%")
 * statCard.setStat3("💨", "Vento",    "12 km/h")
 * ```
 */
class WeatherStatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val tvStat1Icon: TextView
    private val tvStat1Label: TextView
    private val tvStat1Value: TextView
    private val tvStat2Icon: TextView
    private val tvStat2Label: TextView
    private val tvStat2Value: TextView
    private val tvStat3Icon: TextView
    private val tvStat3Label: TextView
    private val tvStat3Value: TextView

    init {
        inflate(context, R.layout.weather_stat_card, this)
        tvStat1Icon  = findViewById(R.id.tv_stat1_icon)
        tvStat1Label = findViewById(R.id.tv_stat1_label)
        tvStat1Value = findViewById(R.id.tv_stat1_value)
        tvStat2Icon  = findViewById(R.id.tv_stat2_icon)
        tvStat2Label = findViewById(R.id.tv_stat2_label)
        tvStat2Value = findViewById(R.id.tv_stat2_value)
        tvStat3Icon  = findViewById(R.id.tv_stat3_icon)
        tvStat3Label = findViewById(R.id.tv_stat3_label)
        tvStat3Value = findViewById(R.id.tv_stat3_value)
    }

    fun setStat1(icon: String, label: String, value: String) {
        tvStat1Icon.text  = icon
        tvStat1Label.text = label
        tvStat1Value.text = value
    }

    fun setStat2(icon: String, label: String, value: String) {
        tvStat2Icon.text  = icon
        tvStat2Label.text = label
        tvStat2Value.text = value
    }

    fun setStat3(icon: String, label: String, value: String) {
        tvStat3Icon.text  = icon
        tvStat3Label.text = label
        tvStat3Value.text = value
    }
}
