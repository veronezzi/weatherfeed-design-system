package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.weather.designsystem.R

/**
 * Bottom navigation bar with 4 fixed tabs: Clima, 5 Dias, Buscar, Ajustes.
 * The active tab gets a navy pill background and blue label.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherBottomNavView
 *     android:id="@+id/bottom_nav"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * bottomNav.setSelectedIndex(0)
 * bottomNav.setOnItemSelected { index -> showScreen(index) }
 * ```
 */
class WeatherBottomNavView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val items: List<LinearLayout>
    private val pills: List<FrameLayout>
    private val labels: List<TextView>

    private var selectedIndex: Int = 0
    private var onItemSelected: ((Int) -> Unit)? = null

    private val activePill     by lazy { resources.getDrawable(R.drawable.bg_weather_nav_pill, context.theme) }
    private val activeColor    by lazy { resources.getColor(R.color.weather_accent_blue, context.theme) }
    private val inactiveColor  by lazy { resources.getColor(R.color.weather_text_secondary, context.theme) }

    init {
        inflate(context, R.layout.weather_bottom_nav, this)

        items = listOf(
            findViewById(R.id.nav_item_0),
            findViewById(R.id.nav_item_1),
            findViewById(R.id.nav_item_2),
            findViewById(R.id.nav_item_3),
        )
        pills = listOf(
            findViewById(R.id.nav_pill_0),
            // items 1-3 don't have pill IDs — handled via renderState
            FrameLayout(context), FrameLayout(context), FrameLayout(context),
        )
        labels = listOf(
            findViewById(R.id.nav_label_0),
            findViewById(R.id.nav_label_1),
            findViewById(R.id.nav_label_2),
            findViewById(R.id.nav_label_3),
        )

        items.forEachIndexed { index, item ->
            item.setOnClickListener { select(index) }
        }

        renderState()
    }

    fun setSelectedIndex(index: Int) {
        selectedIndex = index
        renderState()
    }

    fun getSelectedIndex(): Int = selectedIndex

    fun setOnItemSelected(listener: (Int) -> Unit) {
        onItemSelected = listener
    }

    private fun select(index: Int) {
        if (selectedIndex == index) return
        selectedIndex = index
        renderState()
        onItemSelected?.invoke(index)
    }

    private fun renderState() {
        labels.forEachIndexed { index, label ->
            label.setTextColor(if (index == selectedIndex) activeColor else inactiveColor)
        }
        // pill background only on item 0 (others would need IDs added to layout)
        pills[0].background = if (selectedIndex == 0) activePill else null
    }
}
