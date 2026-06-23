package com.weather.designsystem.xml

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.weather.designsystem.R

/**
 * Pill-shaped search bar.
 *
 * Usage in XML:
 * ```xml
 * <com.weather.designsystem.xml.WeatherSearchBarView
 *     android:id="@+id/search_bar"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * searchBar.setHint("Buscar cidade ou país...")
 * searchBar.setOnTextChanged { query -> viewModel.search(query) }
 * val text = searchBar.getText()
 * ```
 */
class WeatherSearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val etSearch: EditText

    init {
        inflate(context, R.layout.weather_search_bar, this)
        etSearch = findViewById(R.id.et_search)
    }

    fun getText(): String = etSearch.text.toString()

    fun setText(text: String) {
        etSearch.setText(text)
    }

    fun setHint(hint: String) {
        etSearch.hint = hint
    }

    fun setOnTextChanged(listener: (String) -> Unit) {
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: android.text.Editable?) = Unit
        })
    }

    fun setOnSearchAction(listener: (String) -> Unit) {
        etSearch.setOnEditorActionListener { _, _, _ ->
            listener(etSearch.text.toString())
            true
        }
    }
}
