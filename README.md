# Calendar App - Calender.io

A modern, feature-rich calendar application for Android built with Jetpack Compose following clean architecture principles and Material Design 3 guidelines.

## Features

### ✅ Implemented Features

1. **Splash Screen**
   - Beautiful animated splash screen with app branding
   - Smooth transition to main screen
   - Uses Android's SplashScreen API

2. **Calendar View**
   - Custom calendar component using Android's built-in date/time APIs (Java Time API)
   - Month navigation with swipe gestures
   - Event indicators on dates
   - Highlight for today's date
   - Selected date indication
   - Smooth animations for date selection

3. **Event Management**
   - Create events with title, description, time, and color
   - Event list view for selected date
   - Time picker for start and end times
   - Color picker with multiple color options
   - Events stored locally using Room database

4. **Bottom Navigation**
   - Four navigation tabs (Calendar, Clock, Notifications, Profile)
   - Smooth tab transitions
   - Calendar tab fully functional

5. **Localization & RTL Support**
   - Full Arabic localization
   - RTL layout support for Arabic
   - Locale switcher button (EN/AR)
   - Proper date formatting for each locale
   - Calendar grid respects locale-specific first day of week

6. **Animations**
   - Smooth date selection animations
   - Event list item animations
   - Splash screen fade-in animation
   - Month transition animations
   - Swipe gestures for month navigation

## Architecture

### MVVM + Clean Architecture

```
📁 app/src/main/java/com/example/calendar/
├── 📁 data/
│   ├── 📁 local/
│   │   ├── 📁 entity/         # Room entities
│   │   ├── 📁 dao/            # Data Access Objects
│   │   └── CalendarDatabase.kt
│   ├── 📁 mapper/             # Entity to Domain mapping
│   └── 📁 repository/         # Repository implementations
├── 📁 domain/
│   ├── 📁 model/              # Domain models
│   ├── 📁 repository/         # Repository interfaces
│   └── 📁 usecase/            # Business logic use cases
├── 📁 presentation/
│   ├── 📁 components/         # Reusable UI components
│   ├── 📁 screen/             # App screens
│   ├── 📁 viewmodel/          # ViewModels
│   └── 📁 navigation/         # Navigation graph
├── 📁 di/                     # Dependency injection
├── 📁 util/                   # Utility classes
└── 📁 ui/theme/               # Theme and styling
```

## Tech Stack

- **Language**: Kotlin 100%
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Database**: Room
- **Navigation**: Compose Navigation
- **Concurrency**: Kotlin Coroutines & Flow
- **Dependency Injection**: Manual DI (AppModule)
- **Date/Time**: Java Time API (Android's built-in)

## Key Technical Implementations

### 1. Android's Built-in Calendar APIs
The calendar component uses Android's built-in date/time utilities:
- `java.time.LocalDate` for date representation
- `java.time.YearMonth` for month operations
- `java.time.temporal.WeekFields` for locale-aware week calculations
- `CalendarUtils` helper class wrapping Android's calendar functionality

### 2. Room Database
- Events stored locally with Room
- Flow-based reactive queries
- Automatic UI updates when data changes
- Non-blocking database operations

### 3. Clean Architecture Layers

**Data Layer**:
- `EventEntity`: Room database entity
- `EventDao`: Database access interface
- `EventRepositoryImpl`: Repository implementation

**Domain Layer**:
- `Event`: Domain model
- `EventRepository`: Repository interface
- Use cases for each operation (GetEventsByDate, CreateEvent, etc.)

**Presentation Layer**:
- `CalendarViewModel`: State management
- Composable screens and components
- UI state flow

### 4. Localization
- English and Arabic support
- RTL layout direction for Arabic
- Locale-aware date formatting
- First day of week respects locale settings

### 5. No UI Thread Blocking
- All database operations use suspend functions
- Coroutines for async operations
- Flow for reactive data streams
- LaunchedEffect for side effects in Compose

## Design Implementation

The app closely matches the Figma design specifications:
- Purple (#6A5AE0) as primary color
- Light background (#F5F3FF)
- Circular date selection
- Event color indicators
- Smooth rounded corners
- Modern Material Design 3

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 36

### Building the Project
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device

**Or build from command line:**
```bash
./gradlew assembleDebug
```

APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### ✅ Project Status: FULLY RUNNABLE
- ✅ Build successful
- ✅ All compilation errors fixed
- ✅ No linter errors
- ✅ APK generated successfully

### Sample Data
The app automatically populates sample events on first launch for demonstration purposes.

## Project Structure Details

### Database Schema
```kotlin
@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val startTime: Long,  // Seconds of day
    val endTime: Long,    // Seconds of day
    val date: Long,       // Epoch day
    val color: Int
)
```

### State Management
```kotlin
data class CalendarUiState(
    val selectedDate: LocalDate,
    val currentMonth: YearMonth,
    val eventsForSelectedDate: List<Event>,
    val datesWithEvents: Set<LocalDate>,
    val isLoading: Boolean,
    val error: String?
)
```

## Features Demonstrated

### ✅ Animations
- Spring-based date selection animation
- Fade in/out animations
- List item animations
- Swipe gesture animations

### ✅ Swipe Gestures
- Swipe left/right to change months
- Horizontal drag detection
- Threshold-based month switching

### ✅ RTL Support
- Automatic layout mirroring for Arabic
- Locale-aware text rendering
- Bidirectional navigation

## No Third-Party Calendar Libraries
The calendar component is built entirely with:
- Jetpack Compose primitives
- Android's Java Time API
- Material 3 components
- Custom CalendarUtils for date calculations

This ensures:
- No external dependencies for calendar
- Full control over appearance
- Exact match to design specifications
- Optimal performance

## Testing

The project structure supports testing at all layers:
- Unit tests for ViewModels and Use Cases
- Repository tests with Room in-memory database
- UI tests with Compose Testing

## Future Enhancements

Potential additions:
- Event editing and deletion UI
- Event reminders/notifications
- Calendar sync with Google Calendar
- Week/Day view
- Event search and filtering
- Recurring events
- Multiple calendar support

## License

This project is created for demonstration purposes.

---

**Built with ❤️ using Kotlin and Jetpack Compose**

