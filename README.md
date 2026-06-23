# WeatherFeed Design System

Android design system library for the WeatherFeed app — supports both **Jetpack Compose** and **XML Views**.  
Distributed via [JitPack](https://jitpack.io/#veronezzi/weatherfeed-design-system).

[![](https://jitpack.io/v/veronezzi/weatherfeed-design-system.svg)](https://jitpack.io/#veronezzi/weatherfeed-design-system)

---

## Installation

### Step 1 — Add JitPack to your repositories

`settings.gradle.kts`:

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

`app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.veronezzi:weatherfeed-design-system:1.1.0")
}
```

### Step 3 — Wrap your app with the theme (Compose only)

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

For XML projects, apply `Theme.WeatherXmlCatalog` (AppCompat parent) in your manifest or use `@style/WeatherText.*` resources directly — no wrapper needed.

---

## Choosing between Compose and XML

Both implementations expose the same components and design tokens.

| | Compose | XML |
|---|---|---|
| **Package** | `com.weather.designsystem.components.*` | `com.weather.designsystem.xml.*` |
| **Theme** | `WeatherFeedTheme { }` wrapper | `@style/WeatherText.*` + color/dimen resources |
| **Usage** | Composable functions | Custom View classes (extend `FrameLayout`) |
| **Recommended for** | New Compose projects | Legacy XML / View-based projects |

---

## Components

### WeatherTopBar

<details open>
<summary><b>Compose</b></summary>

```kotlin
WeatherTopBar(
    locationText = "São Paulo, Brasil",
    onSearchClick = { /* navigate to search */ },
)
```

</details>

<details>
<summary><b>XML</b></summary>

```xml
<com.weather.designsystem.xml.WeatherTopBarView
    android:id="@+id/top_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

```kotlin
topBar.setLocation("São Paulo, Brasil")
topBar.setOnSearchClick { startActivity(Intent(this, SearchActivity::class.java)) }
```

</details>

---

### WeatherBottomNav

<details open>
<summary><b>Compose</b></summary>

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

</details>

<details>
<summary><b>XML</b></summary>

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

</details>

---

### WeatherCard + SectionLabel

<details open>
<summary><b>Compose</b></summary>

```kotlin
WeatherCard(modifier = Modifier.fillMaxWidth()) {
    SectionLabel("Próximos Dias")
    Text("Conteúdo aqui")
}
```

</details>

<details>
<summary><b>XML</b></summary>

```xml
<LinearLayout
    android:background="@drawable/bg_weather_card"
    android:padding="@dimen/weather_spacing_md"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        style="@style/WeatherText.SectionLabel"
        android:text="PRÓXIMOS DIAS" />

</LinearLayout>
```

</details>

---

### StatCard

<details open>
<summary><b>Compose</b></summary>

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

</details>

<details>
<summary><b>XML</b></summary>

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

</details>

---

### ForecastRow

<details open>
<summary><b>Compose</b></summary>

```kotlin
ForecastRow(
    day = ForecastDay(
        dayName        = "Terça",
        date           = "23 Jun",
        conditionIcon  = "☀️",
        conditionLabel = "Ensolarado",
        tempMax        = "28°",
        tempMin        = "19°",
    ),
    modifier = Modifier.fillMaxWidth(),
)
```

</details>

<details>
<summary><b>XML</b></summary>

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

**In a RecyclerView:**

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

</details>

---

### WeatherSearchBar

<details open>
<summary><b>Compose</b></summary>

```kotlin
var query by remember { mutableStateOf("") }

WeatherSearchBar(
    value         = query,
    onValueChange = { query = it },
    placeholder   = "Buscar cidade ou país...",
    modifier      = Modifier.fillMaxWidth(),
)
```

</details>

<details>
<summary><b>XML</b></summary>

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

</details>

---

### CityRow

<details open>
<summary><b>Compose</b></summary>

```kotlin
CityRow(
    cityName = "São Paulo",
    country  = "Brasil",
    onClick  = { /* handle selection */ },
    modifier = Modifier.fillMaxWidth(),
)
```

</details>

<details>
<summary><b>XML</b></summary>

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

</details>

---

### SettingsRow + TemperatureToggle

<details open>
<summary><b>Compose</b></summary>

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

</details>

<details>
<summary><b>XML</b></summary>

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

</details>

---

### Using XML resources directly

If you prefer full control over your layouts, use the resources directly without the View classes:

```xml
<!-- Card background -->
<LinearLayout
    android:background="@drawable/bg_weather_card"
    android:padding="@dimen/weather_spacing_md" ... />

<!-- Text styles -->
<TextView style="@style/WeatherText.SectionLabel" android:text="PRÓXIMOS DIAS" />
<TextView style="@style/WeatherText.Title"        android:text="São Paulo" />
<TextView style="@style/WeatherText.Body"         android:text="Parcialmente nublado" />
<TextView style="@style/WeatherText.Caption"      android:text="Sensação térmica 26°C" />
```

**Available styles:** `WeatherText.Display` · `WeatherText.Headline` · `WeatherText.Title` · `WeatherText.BodyLarge` · `WeatherText.Body` · `WeatherText.Caption` · `WeatherText.SectionLabel` · `WeatherText.ScreenTitle`

**Available drawables:** `bg_weather_card` · `bg_weather_search_bar` · `bg_weather_stat_card` · `bg_weather_icon_circle` · `bg_weather_nav_pill` · `bg_weather_toggle_track` · `bg_weather_toggle_active`

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

XML equivalents are prefixed with `weather_` — e.g. `@color/weather_accent_blue`.

### Spacing

| Token (Compose) | XML dimen | Value |
|---|---|---|
| `WeatherTheme.spacing.xs` | `@dimen/weather_spacing_xs` | 4 dp |
| `WeatherTheme.spacing.sm` | `@dimen/weather_spacing_sm` | 8 dp |
| `WeatherTheme.spacing.md` | `@dimen/weather_spacing_md` | 16 dp |
| `WeatherTheme.spacing.lg` | `@dimen/weather_spacing_lg` | 24 dp |
| `WeatherTheme.spacing.xl` | `@dimen/weather_spacing_xl` | 32 dp |
| `WeatherTheme.spacing.xxl` | `@dimen/weather_spacing_xxl` | 48 dp |

### Border Radius

| Token (Compose) | XML dimen | Value |
|---|---|---|
| `WeatherTheme.radius.sm` | `@dimen/weather_radius_sm` | 8 dp |
| `WeatherTheme.radius.md` | `@dimen/weather_radius_md` | 16 dp |
| `WeatherTheme.radius.lg` | `@dimen/weather_radius_lg` | 20 dp |
| `WeatherTheme.radius.pill` | `@dimen/weather_radius_pill` | 50 dp |

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

## Releasing a New Version

### 1. Make your changes and commit

```bash
git add .
git commit -m "feat: add new component X"
git push
```

### 2. Bump the version in `designsystem/build.gradle.kts`

```kotlin
version = "1.2.0"   // was 1.1.0
```

Commit and push the bump:

```bash
git add designsystem/build.gradle.kts
git commit -m "chore: bump version to 1.2.0"
git push
```

### 3. Create a GitHub release

```bash
gh release create v1.2.0 --title "v1.2.0 — Description"
```

Or through the GitHub UI: **Releases → Draft a new release → Tag: `v1.2.0` → Publish release**

### 4. JitPack builds automatically

Visit [jitpack.io/#veronezzi/weatherfeed-design-system](https://jitpack.io/#veronezzi/weatherfeed-design-system) and click **Get it** on the new version to trigger the build.

### 5. Users update their dependency

```kotlin
implementation("com.github.veronezzi:weatherfeed-design-system:1.2.0")
```

### Versioning convention

| Change | Example | When |
|---|---|---|
| Patch `1.0.X` | `1.0.1` | Bug fix, no API change |
| Minor `1.X.0` | `1.1.0` | New component, backwards compatible |
| Major `X.0.0` | `2.0.0` | Breaking change (renamed/removed API) |

---

## Project Structure

```
weatherfeed-design-system/
├── designsystem/                        ← library module (published .aar)
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
    └── Toggle Compose/XML on the main screen to browse all components
```

---

## License

MIT © [veronezzi](https://github.com/veronezzi)
