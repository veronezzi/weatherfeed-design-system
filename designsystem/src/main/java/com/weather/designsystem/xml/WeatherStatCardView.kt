package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.weather.designsystem.R

/**
 * Horizontal card showing 3 weather metrics (e.g. Sensação / Umidade / Vento).
 * Each stat has a line icon (default: thermometer, droplet, wind).
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
 * statCard.setStat1(R.drawable.ic_weather_thermometer, "Sensação", "26°")
 * statCard.setStat2(R.drawable.ic_weather_droplet,     "Umidade",  "68%")
 * statCard.setStat3(R.drawable.ic_weather_wind,        "Vento",    "12 km/h")
 * // or keep the default icons:
 * statCard.setStat1("Sensação", "26°")
 * ```
 */
class WeatherStatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val ivStat1Icon: ImageView
    private val tvStat1Label: TextView
    private val tvStat1Value: TextView
    private val ivStat2Icon: ImageView
    private val tvStat2Label: TextView
    private val tvStat2Value: TextView
    private val ivStat3Icon: ImageView
    private val tvStat3Label: TextView
    private val tvStat3Value: TextView

    init {
        inflate(context, R.layout.weather_stat_card, this)
        ivStat1Icon  = findViewById(R.id.iv_stat1_icon)
        tvStat1Label = findViewById(R.id.tv_stat1_label)
        tvStat1Value = findViewById(R.id.tv_stat1_value)
        ivStat2Icon  = findViewById(R.id.iv_stat2_icon)
        tvStat2Label = findViewById(R.id.tv_stat2_label)
        tvStat2Value = findViewById(R.id.tv_stat2_value)
        ivStat3Icon  = findViewById(R.id.iv_stat3_icon)
        tvStat3Label = findViewById(R.id.tv_stat3_label)
        tvStat3Value = findViewById(R.id.tv_stat3_value)
    }

    fun setStat1(label: String, value: String) {
        tvStat1Label.text = label
        tvStat1Value.text = value
    }

    fun setStat2(label: String, value: String) {
        tvStat2Label.text = label
        tvStat2Value.text = value
    }

    fun setStat3(label: String, value: String) {
        tvStat3Label.text = label
        tvStat3Value.text = value
    }

    fun setStat1(@DrawableRes icon: Int, label: String, value: String) {
        ivStat1Icon.setImageResource(icon)
        setStat1(label, value)
    }

    fun setStat2(@DrawableRes icon: Int, label: String, value: String) {
        ivStat2Icon.setImageResource(icon)
        setStat2(label, value)
    }

    fun setStat3(@DrawableRes icon: Int, label: String, value: String) {
        ivStat3Icon.setImageResource(icon)
        setStat3(label, value)
    }

    @Deprecated(
        "Ícones agora são vector drawables do design system; o parâmetro String é ignorado. " +
            "Use setStat1(label, value) ou setStat1(iconRes, label, value).",
        ReplaceWith("setStat1(label, value)"),
    )
    fun setStat1(icon: String, label: String, value: String) = setStat1(label, value)

    @Deprecated(
        "Ícones agora são vector drawables do design system; o parâmetro String é ignorado. " +
            "Use setStat2(label, value) ou setStat2(iconRes, label, value).",
        ReplaceWith("setStat2(label, value)"),
    )
    fun setStat2(icon: String, label: String, value: String) = setStat2(label, value)

    @Deprecated(
        "Ícones agora são vector drawables do design system; o parâmetro String é ignorado. " +
            "Use setStat3(label, value) ou setStat3(iconRes, label, value).",
        ReplaceWith("setStat3(label, value)"),
    )
    fun setStat3(icon: String, label: String, value: String) = setStat3(label, value)
}
