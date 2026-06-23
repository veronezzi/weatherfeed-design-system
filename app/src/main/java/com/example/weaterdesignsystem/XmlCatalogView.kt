package com.example.weaterdesignsystem

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weaterdesignsystem.databinding.ItemXmlSectionBinding
import com.example.weaterdesignsystem.databinding.ViewXmlCatalogBinding
import com.weather.designsystem.xml.WeatherBottomNavView
import com.weather.designsystem.xml.WeatherCityRowView
import com.weather.designsystem.xml.WeatherForecastRowView
import com.weather.designsystem.xml.WeatherSearchBarView
import com.weather.designsystem.xml.WeatherSettingsRowView
import com.weather.designsystem.xml.WeatherStatCardView
import com.weather.designsystem.xml.WeatherTemperatureToggleView
import com.weather.designsystem.xml.WeatherTopBarView

/**
 * Self-contained FrameLayout com todo o catalog de componentes XML.
 * Pode ser embutido em qualquer tela via AndroidView no Compose.
 */
class XmlCatalogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewXmlCatalogBinding
    private val allEntries by lazy { buildEntries() }

    init {
        binding = ViewXmlCatalogBinding.inflate(
            android.view.LayoutInflater.from(context), this, true,
        )
        binding.searchBar.setOnTextChanged { query -> renderList(query) }
        renderList("")
    }

    private fun renderList(query: String) {
        binding.container.removeAllViews()
        val filtered = allEntries.filter {
            query.isBlank() ||
                it.name.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
        }
        filtered.forEach { entry -> binding.container.addView(buildCard(entry)) }
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
            v.setStat1("🌡", "Sensação", "26°")
            v.setStat2("💧", "Umidade",  "68%")
            v.setStat3("💨", "Vento",    "12 km/h")
            container.addView(v, matchWrap())
        },

        XmlComponentEntry("WeatherForecastRowView", "Weather",
            "Linha de previsão de um dia — usada na tela 5 Dias",
        ) { container ->
            listOf(
                arrayOf("Hoje",   "22 Jun", "⛅", "Parc. nublado", "25°", "18°"),
                arrayOf("Terça",  "23 Jun", "☀️", "Ensolarado",    "28°", "19°"),
                arrayOf("Quarta", "24 Jun", "🌧", "Chuvoso",       "21°", "16°"),
            ).forEach { d ->
                val row = WeatherForecastRowView(context)
                row.bind(d[0], d[1], d[2], d[3], d[4], d[5])
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
            row.setIcon("🌡")
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

        XmlComponentEntry("bg_weather_card", "Tokens · Drawables",
            "Background shape arredondado para cards — radius 20dp",
        ) { container ->
            val v = View(context)
            v.setBackgroundResource(com.weather.designsystem.R.drawable.bg_weather_card)
            container.addView(v, LinearLayout.LayoutParams(matchWrap().width, 48.dp))
        },

        XmlComponentEntry("WeatherText styles", "Tokens · Styles",
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

        XmlComponentEntry("Color tokens", "Tokens · Colors",
            "weather_accent_blue, weather_surface_card, etc.",
        ) { container ->
            listOf(
                "Accent Blue #5B9EF0"    to com.weather.designsystem.R.color.weather_accent_blue,
                "Accent Cyan #64B5F6"    to com.weather.designsystem.R.color.weather_accent_cyan,
                "Accent Orange #FF8C42"  to com.weather.designsystem.R.color.weather_accent_orange,
                "Surface Card #1A2040"   to com.weather.designsystem.R.color.weather_surface_card,
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
