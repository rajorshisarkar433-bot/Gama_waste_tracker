# Gama Waste Tracker

**Gama Waste Tracker** is a modern Android application designed to bridge the gap between citizens and municipal waste management services. It provides real-time tracking of collection vehicles and a community-driven platform for reporting sanitation issues.

## 🚀 Key Features

*   **Real-time Tracking:** Live GPS tracking of waste collection tractors ("Kachara Gaadi") on an interactive map.
*   **Proximity Alerts:** Automatic notifications when a collection vehicle is within 500 meters of the user's location.
*   **Blackspot Reporting:** Citizen-led reporting of illegal dumping sites with GPS coordinates, descriptions, and photo uploads.
*   **Digital Waste Guide:** An educational resource for residents on proper waste segregation and disposal techniques.
*   **Live Simulation:** Includes a built-in simulation mode to demonstrate vehicle movement and alert systems without needing live field data.

## 🛠️ Technology Stack

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Database:** Firebase Realtime Database (for live location and report syncing)
*   **Storage:** Firebase Storage (for report images)
*   **Maps:** Google Maps SDK for Android (Maps Compose)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Permissions:** Accompanist Permissions (Location & Camera)
*   **Navigation:** Jetpack Navigation Compose

## 📁 Project Structure

*   `com.example.gamawastetracker.ui`: Contains all Compose screens (`MapScreen`, `ReportScreen`, `GuideScreen`) and the `MainViewModel`.
*   `com.example.gamawastetracker.data`: Data models for `TractorLocation` and `BlackspotReport`.
*   `com.example.gamawastetracker.ui.theme`: Material 3 design system implementation.

## ⚙️ Setup & Installation

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/your-repo/gamawastetracker.git
    ```
2.  **Firebase Configuration:**
    *   Create a project in the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android App with package name `com.example.gamawastetracker`.
    *   Download `google-services.json` and place it in the `app/` directory.
    *   Enable **Realtime Database** and **Storage**.
3.  **Google Maps API:**
    *   Get an API Key from the [Google Cloud Console](https://console.cloud.google.com/).
    *   Add the key to your `local.properties` or `AndroidManifest.xml`.
4.  **Build & Run:**
    *   Open the project in Android Studio (Hedgehog or newer recommended).
    *   Sync Gradle and click **Run**.

## 💡 How it Works

1.  **Tracking:** The `MainViewModel` listens to a Firebase reference (`/tractors`). When vehicle coordinates update, the `MapScreen` recomposes to show the new position.
2.  **Alerts:** The app calculates the distance between the user's fixed coordinate and the tractor's live coordinate using `android.location.Location.distanceBetween`.
3.  **Reporting:** When a user submits a report, the data is pushed to the `/blackspots` node in Firebase, making it instantly visible to other users and administrators.

---
*Created for the Gama Waste Tracker Project.*
