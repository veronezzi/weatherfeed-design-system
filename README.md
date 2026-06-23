# WeatherFeed Design System

Android design system library built with **Jetpack Compose** for the WeatherFeed app.  
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

## Components

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
├── designsystem/               ← library module (the .aar)
│   └── src/main/java/com/weather/designsystem/
│       ├── theme/
│       │   ├── Color.kt
│       │   ├── Typography.kt
│       │   ├── Spacing.kt
│       │   └── Theme.kt        ← WeatherFeedTheme
│       └── components/
│           ├── WeatherCard.kt
│           ├── StatCard.kt
│           ├── ForecastRow.kt
│           ├── SearchBar.kt
│           ├── CityRow.kt
│           ├── SettingsRow.kt
│           ├── BottomNav.kt
│           └── TopBar.kt
└── app/                        ← showcase app (component catalog)
```

---

## License

MIT © [veronezzi](https://github.com/veronezzi)
