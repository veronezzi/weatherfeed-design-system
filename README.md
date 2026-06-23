# WeatherFeed Design System

Android design system library for the WeatherFeed app — supports both **Jetpack Compose** and **XML Views**.  
Distributed via [JitPack](https://jitpack.io/#veronezzi/weatherfeed-design-system).

[![](https://jitpack.io/v/veronezzi/weatherfeed-design-system.svg)](https://jitpack.io/#veronezzi/weatherfeed-design-system)

---

## Installation

### Step 1 — Add JitPack to your repositories

In your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2 — Add the dependency

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.veronezzi:weatherfeed-design-system:1.0.0")
}
```

### Step 3 — Wrap your app with the theme

```kotlin
import com.weather.designsystem.theme.WeatherFeedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherFeedTheme {
                // your screens here
            }
        }
    }
}
```

---

## Choosing between Compose and XML

Both implementations expose the same components and design tokens.

| | Compose | XML |
|---|---|---|
| **Import** | `com.weather.designsystem.components.*` | `com.weather.designsystem.xml.*` |
| **Theme** | `WeatherFeedTheme { }` wrapper | `@style/WeatherText.*` + color/dimen resources |
| **Usage style** | Composable functions | Custom View classes inflated from XML |
| **Recommended for** | New Compose projects | Legacy XML / View-based projects |

---

## Components — Compose

### WeatherTopBar
Top bar with location text and search button.

```kotlin
WeatherTopBar(
    locationText = "São Paulo, Brasil",
    onSearchClick = { /* navigate to search */ },
)
```

---

### WeatherBottomNav
Bottom navigation bar with 4 tabs. Active tab gets a pill highlight.

```kotlin
val navItems = listOf(
    NavItem("🌤", "Clima"),
    NavItem("📅", "5 Dias"),
    NavItem("🔍", "Buscar"),
    NavItem("⚙️", "Ajustes"),
)

var selectedIndex by remember { mutableIntStateOf(0) }

WeatherBottomNav(
    items = navItems,
    selectedIndex = selectedIndex,
    onItemSelected = { selectedIndex = it },
)
```

---

### WeatherCard + SectionLabel
Base container card with an optional section label.

```kotlin
WeatherCard(modifier = Modifier.fillMaxWidth()) {
    SectionLabel("Próximos Dias")
    Text("Conteúdo aqui")
}
```

---

### StatCard
Horizontal card with 3 weather metrics (Sensação, Umidade, Vento).

```kotlin
StatCard(
    stats = listOf(
        WeatherStat(icon = "🌡", label = "Sensação", value = "26°"),
        WeatherStat(icon = "💧", label = "Umidade",  value = "68%"),
        WeatherStat(icon = "💨", label = "Vento",    value = "12 km/h"),
    ),
    modifier = Modifier.fillMaxWidth(),
)
```

---

### ForecastRow
Single day forecast row used in the 5-day forecast screen.

```kotlin
ForecastRow(
    day = ForecastDay(
        dayName       = "Terça",
        date          = "23 Jun",
        conditionIcon = "☀️",
        conditionLabel = "Ensolarado",
        tempMax       = "28°",
        tempMin       = "19°",
    ),
    modifier = Modifier.fillMaxWidth(),
)
```

---

### WeatherSearchBar
Pill-shaped search bar for the search screen.

```kotlin
var query by remember { mutableStateOf("") }

WeatherSearchBar(
    value         = query,
    onValueChange = { query = it },
    placeholder   = "Buscar cidade ou país...",
    modifier      = Modifier.fillMaxWidth(),
)
```

---

### CityRow
Search result item with a location pin icon.

```kotlin
CityRow(
    cityName = "São Paulo",
    country  = "Brasil",
    onClick  = { /* handle selection */ },
    modifier = Modifier.fillMaxWidth(),
)
```

---

### SettingsRow + TemperatureToggle
Settings row with icon, title, subtitle and a custom trailing slot.  
`TemperatureToggle` is the built-in °C / °F segmented control.

```kotlin
var isCelsius by remember { mutableStateOf(true) }

SettingsRow(
    icon     = "🌡",
    title    = "Unidade de temperatura",
    subtitle = "Celsius ou Fahrenheit",
    modifier = Modifier.fillMaxWidth(),
    trailing = {
        TemperatureToggle(
            isCelsius = isCelsius,
            onToggle  = { isCelsius = it },
        )
    },
)
```

---

---

## Components — XML Views

All components live in the `com.weather.designsystem.xml` package and extend `FrameLayout`, so they work in any XML layout or can be created programmatically.

### WeatherTopBarView

```xml
<com.weather.designsystem.xml.WeatherTopBarView
    android:id="@+id/top_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
topBar.setLocation("São Paulo, Brasil")
topBar.setOnSearchClick { startActivity(SearchActivity::class) }
```

---

### WeatherBottomNavView

```xml
<com.weather.designsystem.xml.WeatherBottomNavView
    android:id="@+id/bottom_nav"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
bottomNav.setSelectedIndex(0)
bottomNav.setOnItemSelected { index ->
    when (index) {
        0 -> showClima()
        1 -> showFiveDays()
        2 -> showSearch()
        3 -> showSettings()
    }
}
```

---

### WeatherStatCardView

```xml
<com.weather.designsystem.xml.WeatherStatCardView
    android:id="@+id/stat_card"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
statCard.setStat1("🌡", "Sensação", "26°")
statCard.setStat2("💧", "Umidade",  "68%")
statCard.setStat3("💨", "Vento",    "12 km/h")
```

---

### WeatherForecastRowView

```xml
<com.weather.designsystem.xml.WeatherForecastRowView
    android:id="@+id/forecast_row"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
forecastRow.bind(
    dayName        = "Terça",
    date           = "23 Jun",
    conditionIcon  = "☀️",
    conditionLabel = "Ensolarado",
    tempMax        = "28°",
    tempMin        = "19°",
)
```

For lists, use it inside a `RecyclerView` adapter:

```kotlin
class ForecastAdapter(private val items: List<ForecastItem>) :
    RecyclerView.Adapter<ForecastAdapter.VH>() {

    inner class VH(val view: WeatherForecastRowView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(WeatherForecastRowView(parent.context))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.view.bind(item.dayName, item.date, item.icon, item.label, item.max, item.min)
    }

    override fun getItemCount() = items.size
}
```

---

### WeatherSearchBarView

```xml
<com.weather.designsystem.xml.WeatherSearchBarView
    android:id="@+id/search_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
searchBar.setHint("Buscar cidade ou país...")
searchBar.setOnTextChanged { query -> viewModel.search(query) }
searchBar.setOnSearchAction { query -> viewModel.submit(query) }
```

---

### WeatherCityRowView

```xml
<com.weather.designsystem.xml.WeatherCityRowView
    android:id="@+id/city_row"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
cityRow.bind("São Paulo", "Brasil")
cityRow.setOnClickListener { openCity("São Paulo") }
```

---

### WeatherSettingsRowView + WeatherTemperatureToggleView

```xml
<com.weather.designsystem.xml.WeatherSettingsRowView
    android:id="@+id/settings_row"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
settingsRow.setIcon("🌡")
settingsRow.setTitle("Unidade de temperatura")
settingsRow.setSubtitle("Celsius ou Fahrenheit")

val toggle = WeatherTemperatureToggleView(context)
toggle.setUnit(isCelsius = true)
toggle.setOnUnitChanged { isCelsius -> viewModel.setUnit(isCelsius) }
settingsRow.setTrailing(toggle)
```

---

### Using XML styles and drawables directly

If you prefer full control over your layouts, you can use the resources directly:

```xml
<!-- Card background -->
<LinearLayout
    android:background="@drawable/bg_weather_card"
    android:padding="@dimen/weather_spacing_md" ... />

<!-- Section label -->
<TextView
    style="@style/WeatherText.SectionLabel"
    android:text="PRÓXIMOS DIAS" />

<!-- Title -->
<TextView
    style="@style/WeatherText.Title"
    android:text="São Paulo" />

<!-- Body / caption -->
<TextView
    style="@style/WeatherText.Body"
    android:text="Parcialmente nublado" />

<TextView
    style="@style/WeatherText.Caption"
    android:text="Sensação térmica 26°C" />
```

Available styles: `WeatherText.Display`, `WeatherText.Headline`, `WeatherText.Title`,
`WeatherText.BodyLarge`, `WeatherText.Body`, `WeatherText.Caption`, `WeatherText.SectionLabel`, `WeatherText.ScreenTitle`

Available drawables: `bg_weather_card`, `bg_weather_search_bar`, `bg_weather_stat_card`,
`bg_weather_icon_circle`, `bg_weather_nav_pill`, `bg_weather_toggle_track`, `bg_weather_toggle_active`

---

## Design Tokens

### Colors

| Token | Hex | Usage |
|---|---|---|
| `GradientStart` | `#3B4BC8` | Home screen gradient start |
| `GradientEnd` | `#7B2FCA` | Home screen gradient end |
| `BackgroundDark` | `#0D1230` | Secondary screen backgrounds |
| `SurfaceCard` | `#1A2040` | Card and row backgrounds |
| `AccentBlue` | `#5B9EF0` | Active state, nav highlight |
| `AccentCyan` | `#64B5F6` | Section labels |
| `AccentOrange` | `#FF8C42` | Settings screen title |
| `TextPrimary` | `#FFFFFF` | Main text |
| `TextSecondary` | `#8892B0` | Subtitles and captions |

### Spacing (`WeatherTheme.spacing`)

| Token | Value |
|---|---|
| `xs` | 4 dp |
| `sm` | 8 dp |
| `md` | 16 dp |
| `lg` | 24 dp |
| `xl` | 32 dp |
| `xxl` | 48 dp |

### Border Radius (`WeatherTheme.radius`)

| Token | Value |
|---|---|
| `sm` | 8 dp |
| `md` | 16 dp |
| `lg` | 20 dp |
| `pill` | 50 dp |

### Typography

| Style | Size | Usage |
|---|---|---|
| `displayLarge` | 72 sp Bold | Main temperature "24°" |
| `headlineMedium` | 24 sp Bold | Screen titles |
| `titleMedium` | 17 sp SemiBold | City name, day name |
| `bodyLarge` | 16 sp | Condition description |
| `bodyMedium` | 14 sp | Subtitles |
| `bodySmall` | 12 sp | Captions, stat labels |
| `labelSmall` | 11 sp SemiBold Uppercase | Section labels |

---

## Releasing a New Version (bump guide)

### 1. Make your changes and commit

```bash
git add .
git commit -m "feat: add new component X"
git push
```

### 2. Create a new GitHub release (tag = new version)

```bash
gh release create 1.1.0 --title "v1.1.0 - Description of changes"
```

Or through the GitHub UI:  
**Releases → Draft a new release → Tag: `1.1.0` → Publish release**

### 3. JitPack builds automatically

Visit [jitpack.io/#veronezzi/weatherfeed-design-system](https://jitpack.io/#veronezzi/weatherfeed-design-system) and click **Get it** on the new version to trigger the build. Wait for the green badge.

### 4. Users update their dependency

```kotlin
// bump the version number
implementation("com.github.veronezzi:weatherfeed-design-system:1.1.0")
```

### Versioning convention

Follow [Semantic Versioning](https://semver.org/):

| Change | Example | When to use |
|---|---|---|
| Patch `1.0.X` | `1.0.1` | Bug fix, no API change |
| Minor `1.X.0` | `1.1.0` | New component, backwards compatible |
| Major `X.0.0` | `2.0.0` | Breaking change (renamed/removed API) |

---

## Project Structure

```
weatherfeed-design-system/
├── designsystem/                        ← library module (the .aar)
│   └── src/main/
│       ├── java/com/weather/designsystem/
│       │   ├── theme/                   ← Compose tokens
│       │   │   ├── Color.kt
│       │   │   ├── Typography.kt
│       │   │   ├── Spacing.kt
│       │   │   └── Theme.kt             ← WeatherFeedTheme { }
│       │   ├── components/              ← Compose components
│       │   │   ├── WeatherCard.kt
│       │   │   ├── StatCard.kt
│       │   │   ├── ForecastRow.kt
│       │   │   ├── SearchBar.kt
│       │   │   ├── CityRow.kt
│       │   │   ├── SettingsRow.kt
│       │   │   ├── BottomNav.kt
│       │   │   └── TopBar.kt
│       │   └── xml/                     ← XML / View components
│       │       ├── WeatherTopBarView.kt
│       │       ├── WeatherStatCardView.kt
│       │       ├── WeatherForecastRowView.kt
│       │       ├── WeatherSearchBarView.kt
│       │       ├── WeatherCityRowView.kt
│       │       ├── WeatherSettingsRowView.kt
│       │       ├── WeatherTemperatureToggleView.kt
│       │       └── WeatherBottomNavView.kt
│       └── res/
│           ├── layout/                  ← XML layouts for each view
│           ├── drawable/                ← bg_weather_*.xml shapes
│           └── values/
│               ├── weather_colors.xml
│               ├── weather_dimens.xml
│               └── weather_styles.xml
└── app/                                 ← showcase app (component catalog)
```

---

## License

MIT © [veronezzi](https://github.com/veronezzi)
