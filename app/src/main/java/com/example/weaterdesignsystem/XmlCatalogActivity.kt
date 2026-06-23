package com.example.weaterdesignsystem

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weaterdesignsystem.databinding.ActivityXmlCatalogBinding
import com.example.weaterdesignsystem.databinding.ItemXmlSectionBinding
import com.weather.designsystem.xml.WeatherBottomNavView
import com.weather.designsystem.xml.WeatherCityRowView
import com.weather.designsystem.xml.WeatherForecastRowView
import com.weather.designsystem.xml.WeatherSearchBarView
import com.weather.designsystem.xml.WeatherSettingsRowView
import com.weather.designsystem.xml.WeatherStatCardView
import com.weather.designsystem.xml.WeatherTemperatureToggleView
import com.weather.designsystem.xml.WeatherTopBarView

data class XmlComponentEntry(
    val name: String,
    val category: String,
    val description: String,
    val buildPreview: (LinearLayout) -> Unit,
)

class XmlCatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityXmlCatalogBinding
    private val allEntries by lazy { buildEntries() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXmlCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

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
        val card = ItemXmlSectionBinding.inflate(layoutInflater, binding.container, false)
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

        XmlComponentEntry(
            name = "WeatherTopBarView",
            category = "Navigation",
            description = "Barra superior com localização e botão de busca",
        ) { container ->
            val view = WeatherTopBarView(this)
            view.setLocation("São Paulo, Brasil")
            container.addView(view)
        },

        XmlComponentEntry(
            name = "WeatherBottomNavView",
            category = "Navigation",
            description = "Navegação inferior com 4 abas e pill no item ativo",
        ) { container ->
            val view = WeatherBottomNavView(this)
            view.setSelectedIndex(0)
            container.addView(view)
        },

        XmlComponentEntry(
            name = "WeatherStatCardView",
            category = "Weather",
            description = "Card horizontal com 3 métricas: Sensação, Umidade, Vento",
        ) { container ->
            val view = WeatherStatCardView(this)
            view.setStat1("🌡", "Sensação", "26°")
            view.setStat2("💧", "Umidade",  "68%")
            view.setStat3("💨", "Vento",    "12 km/h")
            container.addView(view, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ))
        },

        XmlComponentEntry(
            name = "WeatherForecastRowView",
            category = "Weather",
            description = "Linha de previsão de um dia — usada na tela 5 Dias",
        ) { container ->
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply { bottomMargin = 8.dp }

            listOf(
                arrayOf("Hoje",   "22 Jun", "⛅", "Parc. nublado", "25°", "18°"),
                arrayOf("Terça",  "23 Jun", "☀️", "Ensolarado",    "28°", "19°"),
                arrayOf("Quarta", "24 Jun", "🌧", "Chuvoso",       "21°", "16°"),
            ).forEach { d ->
                val row = WeatherForecastRowView(this)
                row.bind(d[0], d[1], d[2], d[3], d[4], d[5])
                container.addView(row, params)
            }
        },

        XmlComponentEntry(
            name = "WeatherSearchBarView",
            category = "Inputs",
            description = "Barra de busca pill — tela Buscar",
        ) { container ->
            val view = WeatherSearchBarView(this)
            view.setHint("Buscar cidade ou país...")
            container.addView(view, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ))
        },

        XmlComponentEntry(
            name = "WeatherCityRowView",
            category = "Inputs",
            description = "Item de resultado de cidade com pin e país",
        ) { container ->
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply { bottomMargin = 8.dp }

            listOf("São Paulo" to "Brasil", "Lisboa" to "Portugal", "Madrid" to "Espanha")
                .forEach { (city, country) ->
                    val row = WeatherCityRowView(this)
                    row.bind(city, country)
                    container.addView(row, params)
                }
        },

        XmlComponentEntry(
            name = "WeatherSettingsRowView",
            category = "Settings",
            description = "Linha de configuração com ícone, título, subtítulo e trailing",
        ) { container ->
            val row = WeatherSettingsRowView(this)
            row.setIcon("🌡")
            row.setTitle("Unidade de temperatura")
            row.setSubtitle("Celsius ou Fahrenheit")
            val toggle = WeatherTemperatureToggleView(this)
            toggle.setUnit(isCelsius = true)
            row.setTrailing(toggle)
            container.addView(row, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ))
        },

        XmlComponentEntry(
            name = "WeatherTemperatureToggleView",
            category = "Settings",
            description = "Seletor segmentado °C / °F",
        ) { container ->
            val toggle = WeatherTemperatureToggleView(this)
            toggle.setUnit(isCelsius = true)
            toggle.setOnUnitChanged { isCelsius ->
                toggle.setUnit(isCelsius)
            }
            container.addView(toggle)
        },

        XmlComponentEntry(
            name = "bg_weather_card",
            category = "Tokens · Drawables",
            description = "Background shape arredondado para cards — radius 20dp",
        ) { container ->
            val view = View(this)
            view.setBackgroundResource(com.weather.designsystem.R.drawable.bg_weather_card)
            container.addView(view, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 48.dp,
            ))
        },

        XmlComponentEntry(
            name = "WeatherText styles",
            category = "Tokens · Styles",
            description = "Estilos de texto disponíveis via @style/WeatherText.*",
        ) { container ->
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply { bottomMargin = 4.dp }

            mapOf(
                "SECTION LABEL 11sp" to com.weather.designsystem.R.style.WeatherText_SectionLabel,
                "Title 17sp" to com.weather.designsystem.R.style.WeatherText_Title,
                "Body 14sp" to com.weather.designsystem.R.style.WeatherText_Body,
                "Caption 12sp" to com.weather.designsystem.R.style.WeatherText_Caption,
            ).forEach { (text, styleRes) ->
                val tv = TextView(this, null, 0, styleRes)
                tv.text = text
                container.addView(tv, params)
            }
        },

        XmlComponentEntry(
            name = "Color tokens",
            category = "Tokens · Colors",
            description = "Tokens de cor — weather_accent_blue, weather_surface_card, etc.",
        ) { container ->
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 36.dp,
            ).apply { bottomMargin = 4.dp }

            listOf(
                "Accent Blue #5B9EF0" to com.weather.designsystem.R.color.weather_accent_blue,
                "Accent Cyan #64B5F6" to com.weather.designsystem.R.color.weather_accent_cyan,
                "Accent Orange #FF8C42" to com.weather.designsystem.R.color.weather_accent_orange,
                "Surface Card #1A2040" to com.weather.designsystem.R.color.weather_surface_card,
                "Background Dark #0D1230" to com.weather.designsystem.R.color.weather_background_dark,
            ).forEach { (label, colorRes) ->
                val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
                val swatch = View(this).apply {
                    setBackgroundColor(resources.getColor(colorRes, theme))
                }
                val tv = TextView(this, null, 0, com.weather.designsystem.R.style.WeatherText_Caption).apply {
                    text = label
                    setPadding(12.dp, 0, 0, 0)
                }
                row.addView(swatch, LinearLayout.LayoutParams(36.dp, LinearLayout.LayoutParams.MATCH_PARENT))
                row.addView(tv, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
                container.addView(row, params)
            }
        },
    )

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()
}
