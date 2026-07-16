package com.example.weaterdesignsystem

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weaterdesignsystem.databinding.ItemXmlSectionBinding
import com.example.weaterdesignsystem.databinding.ViewXmlCatalogBinding
import com.weather.designsystem.WeatherConditionIcons
import com.weather.designsystem.xml.WeatherBottomNavView
import com.weather.designsystem.xml.WeatherCityRowView
import com.weather.designsystem.xml.WeatherForecastRowView
import com.weather.designsystem.xml.WeatherSearchBarView
import com.weather.designsystem.xml.WeatherSettingsRowView
import com.weather.designsystem.xml.WeatherStatCardView
import com.weather.designsystem.xml.WeatherTemperatureToggleView
import com.weather.designsystem.xml.WeatherTopBarView

class XmlCatalogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewXmlCatalogBinding
    private val allEntries by lazy { buildEntries() }
    private var selectedCategory = "Todos"
    private var currentQuery = ""

    init {
        binding = ViewXmlCatalogBinding.inflate(
            android.view.LayoutInflater.from(context), this, true,
        )
        binding.searchBar.setHint("Buscar componente...")
        binding.searchBar.setOnTextChanged { query ->
            currentQuery = query
            renderList()
        }
        buildChips()
        renderList()
    }

    private fun buildChips() {
        val categories = listOf("Todos") + allEntries.map { it.category }.distinct()
        binding.chipContainer.removeAllViews()
        categories.forEach { cat ->
            val chip = makeChip(cat)
            chip.setOnClickListener {
                selectedCategory = cat
                buildChips()
                renderList()
            }
            binding.chipContainer.addView(chip, chipParams())
        }
    }

    private fun makeChip(label: String): TextView {
        val isSelected = label == selectedCategory
        val bgColor  = if (isSelected) 0xFF2D3A6E.toInt() else 0xFF1A2040.toInt()
        val textColor = if (isSelected) 0xFF64B5F6.toInt() else 0xFF8892B0.toInt()

        val bg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f * resources.displayMetrics.density
            setColor(bgColor)
        }

        return TextView(context).apply {
            text = label
            setTextColor(textColor)
            textSize = 12f
            background = bg
            val hPad = (12 * resources.displayMetrics.density).toInt()
            val vPad = (6  * resources.displayMetrics.density).toInt()
            setPadding(hPad, vPad, hPad, vPad)
        }
    }

    private fun chipParams() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
    ).also { it.marginEnd = (8 * resources.displayMetrics.density).toInt() }

    private fun renderList() {
        binding.container.removeAllViews()
        allEntries
            .filter { entry ->
                val matchesQuery = currentQuery.isBlank() ||
                    entry.name.contains(currentQuery, ignoreCase = true) ||
                    entry.description.contains(currentQuery, ignoreCase = true)
                val matchesCat = selectedCategory == "Todos" || entry.category == selectedCategory
                matchesQuery && matchesCat
            }
            .forEach { entry -> binding.container.addView(buildCard(entry)) }
    }

    private fun buildCard(entry: XmlComponentEntry): View {
        val card = ItemXmlSectionBinding.inflate(
            android.view.LayoutInflater.from(context), binding.container, false,
        )
        card.tvComponentName.text = entry.name
        card.tvDescription.text   = entry.description
        card.tvCategory.text      = entry.category

        var expanded = false
        card.llHeader.setOnClickListener {
            expanded = !expanded
            if (expanded) {
                card.llPreview.removeAllViews()
                entry.buildPreview(card.llPreview)
                card.llPreview.visibility = View.VISIBLE
            } else {
                card.llPreview.visibility = View.GONE
            }
        }
        return card.root
    }

    private fun buildEntries() = listOf(

        XmlComponentEntry("WeatherTopBarView", "Navigation",
            "Barra superior com localização e botão de busca",
        ) { container ->
            val v = WeatherTopBarView(context)
            v.setLocation("São Paulo, Brasil")
            container.addView(v)
        },

        XmlComponentEntry("WeatherBottomNavView", "Navigation",
            "Navegação inferior com 4 abas e pill no item ativo",
        ) { container ->
            val v = WeatherBottomNavView(context)
            v.setSelectedIndex(0)
            container.addView(v)
        },

        XmlComponentEntry("WeatherStatCardView", "Weather",
            "Card horizontal com 3 métricas: Sensação, Umidade, Vento",
        ) { container ->
            val v = WeatherStatCardView(context)
            v.setStat1("Sensação", "26°")
            v.setStat2("Umidade",  "68%")
            v.setStat3("Vento",    "12 km/h")
            container.addView(v, matchWrap())
        },

        XmlComponentEntry("WeatherForecastRowView", "Weather",
            "Linha de previsão de um dia — usada na tela 5 Dias",
        ) { container ->
            listOf(
                ForecastDemo("Hoje",   "22 Jun", "02d", "Parc. nublado", "25°", "18°"),
                ForecastDemo("Terça",  "23 Jun", "01d", "Ensolarado",    "28°", "19°"),
                ForecastDemo("Quarta", "24 Jun", "10d", "Chuvoso",       "21°", "16°"),
            ).forEach { d ->
                val row = WeatherForecastRowView(context)
                row.bind(
                    d.day,
                    d.date,
                    WeatherConditionIcons.fromOpenWeather(d.code),
                    d.label,
                    d.max,
                    d.min,
                )
                container.addView(row, matchWrap(bottomMargin = 8.dp))
            }
        },

        XmlComponentEntry("WeatherSearchBarView", "Inputs",
            "Barra de busca pill — tela Buscar",
        ) { container ->
            val v = WeatherSearchBarView(context)
            v.setHint("Buscar cidade ou país...")
            container.addView(v, matchWrap())
        },

        XmlComponentEntry("WeatherCityRowView", "Inputs",
            "Item de resultado de cidade com pin e país",
        ) { container ->
            listOf("São Paulo" to "Brasil", "Lisboa" to "Portugal", "Madrid" to "Espanha")
                .forEach { (city, country) ->
                    val row = WeatherCityRowView(context)
                    row.bind(city, country)
                    container.addView(row, matchWrap(bottomMargin = 8.dp))
                }
        },

        XmlComponentEntry("WeatherSettingsRowView", "Settings",
            "Linha de configuração com ícone, título, subtítulo e trailing",
        ) { container ->
            val row = WeatherSettingsRowView(context)
            row.setIcon(com.weather.designsystem.R.drawable.ic_weather_thermometer)
            row.setTitle("Unidade de temperatura")
            row.setSubtitle("Celsius ou Fahrenheit")
            val toggle = WeatherTemperatureToggleView(context)
            toggle.setUnit(isCelsius = true)
            toggle.setOnUnitChanged { toggle.setUnit(it) }
            row.setTrailing(toggle)
            container.addView(row, matchWrap())
        },

        XmlComponentEntry("WeatherTemperatureToggleView", "Settings",
            "Seletor segmentado °C / °F",
        ) { container ->
            val toggle = WeatherTemperatureToggleView(context)
            toggle.setUnit(isCelsius = true)
            toggle.setOnUnitChanged { toggle.setUnit(it) }
            container.addView(toggle)
        },

        XmlComponentEntry("bg_weather_card", "Tokens",
            "Background shape arredondado para cards — radius 20dp",
        ) { container ->
            val v = View(context)
            v.setBackgroundResource(com.weather.designsystem.R.drawable.bg_weather_card)
            container.addView(v, LinearLayout.LayoutParams(matchWrap().width, 48.dp))
        },

        XmlComponentEntry("WeatherText styles", "Tokens",
            "Estilos de texto disponíveis via @style/WeatherText.*",
        ) { container ->
            mapOf(
                "SECTION LABEL 11sp" to com.weather.designsystem.R.style.WeatherText_SectionLabel,
                "Title 17sp"         to com.weather.designsystem.R.style.WeatherText_Title,
                "Body 14sp"          to com.weather.designsystem.R.style.WeatherText_Body,
                "Caption 12sp"       to com.weather.designsystem.R.style.WeatherText_Caption,
            ).forEach { (text, styleRes) ->
                val tv = TextView(context, null, 0, styleRes)
                tv.text = text
                container.addView(tv, wrapWrap(bottomMargin = 4.dp))
            }
        },

        XmlComponentEntry("Color tokens", "Tokens",
            "weather_accent_blue, weather_surface_card, etc.",
        ) { container ->
            listOf(
                "Accent Blue #5B9EF0"     to com.weather.designsystem.R.color.weather_accent_blue,
                "Accent Cyan #64B5F6"     to com.weather.designsystem.R.color.weather_accent_cyan,
                "Accent Orange #FF8C42"   to com.weather.designsystem.R.color.weather_accent_orange,
                "Surface Card #1A2040"    to com.weather.designsystem.R.color.weather_surface_card,
                "Background Dark #0D1230" to com.weather.designsystem.R.color.weather_background_dark,
            ).forEach { (label, colorRes) ->
                val row = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
                val swatch = View(context).apply {
                    setBackgroundColor(resources.getColor(colorRes, context.theme))
                }
                val tv = TextView(context, null, 0, com.weather.designsystem.R.style.WeatherText_Caption).apply {
                    text = label
                    setPadding(12.dp, 0, 0, 0)
                }
                row.addView(swatch, LinearLayout.LayoutParams(36.dp, LinearLayout.LayoutParams.MATCH_PARENT))
                row.addView(tv, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
                container.addView(row, matchWrap(bottomMargin = 4.dp).also { it.height = 36.dp })
            }
        },
    )

    private fun matchWrap(bottomMargin: Int = 0) = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
    ).also { it.bottomMargin = bottomMargin }

    private fun wrapWrap(bottomMargin: Int = 0) = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
    ).also { it.bottomMargin = bottomMargin }

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()
}

private data class ForecastDemo(
    val day: String,
    val date: String,
    val code: String,
    val label: String,
    val max: String,
    val min: String,
)
