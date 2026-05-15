# Namma-Kathey (National Pride)

## Project Overview
**Namma-Kathey** is an interactive educational Android application designed for children to explore the rich historical heritage of Karnataka. The app serves as a "Regional Hero Storybook," featuring legends from every district, including freedom fighters, poets, and social reformers.

## Key Features
- **District-wise Exploration**: Discover local heroes from over 15 districts of Karnataka.
- **Bilingual Experience**: Full support for both English and Kannada languages with an easy toggle.
- **Interactive Storybook**: Swipe-based story navigation with rich illustrations and detailed historical accounts.
- **Audio Learning**: Integrated Text-To-Speech (TTS) engine to read stories aloud in both languages.
- **Gamified Quizzes**: Hero-specific quizzes that reward users with "Heritage Badges" upon completion.
- **Historic UI**: A custom-designed "Regal" interface using a heritage color palette (Royal Red & Antique Gold).
- **Statue Finder**: Provides location descriptions and coordinates for historical memorials and statues.

## Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Navigation**: Jetpack Navigation Component for Compose
- **Data Management**: KotlinX Serialization (JSON-based local database)
- **Image Loading**: Coil (with SubcomposeAsyncImage for loading states)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Permissions**: Internet access for dynamic image fetching.

## Installation & Setup
1. **Clone the Project**: Download or clone the source code to your local machine.
2. **Open in Android Studio**: Use Android Studio Ladybug (2024.2.1) or a newer version.
3. **SDK Requirements**: Ensure you have **Android API 36** (VanillaIceCream) or later installed in your SDK Manager.
4. **Sync Gradle**: Allow the project to download all necessary dependencies.
5. **Run**: Deploy the app to an emulator (API 30+) or a physical device.

## Project Structure
- `MainActivity.kt`: The entry point and navigation host.
- `MainViewModel.kt`: Manages app state, language settings, and user progress.
- `data/`: Contains data models, the repository for JSON loading, and the TTS manager.
- `ui/screens/`: Individual Compose-based screens for a modular UI.
- `assets/data.json`: The central database containing all historical content.

## Impact Goals
- **Patriotism**: Building a deep connection with India's diverse freedom struggle.
- **Value Education**: Teaching courage, honesty, and social service through local examples.
- **Linguistic Heritage**: Promoting reading and literacy in the mother tongue (Kannada).

---
*Developed for the MindMatrix VTU Internship Program.*
