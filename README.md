# 📱 Business Card Android Application

[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4CAF50.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android SDK](https://img.shields.io/badge/Android%20SDK-37-brightgreen.svg)](https://developer.android.com)

A modern, elegant, and fully interactive digital business card Android application built from scratch using **Kotlin** and **Jetpack Compose (Material 3)**. Designed for professionals, developers, and tech enthusiasts to share their portfolios, social profiles, and contact information seamlessly on the go.

---
## ✨ Features

- **🎨 Modern Material 3 UI:** Built using Jetpack Compose with sleek dark mode aesthetics, vibrant green accents, and custom typography.
- **🔗 Interactive In-App Web Navigation:** One-tap navigation to Sessionize, X (Twitter), LinkedIn, and GitHub profiles inside an embedded WebView with dedicated Back buttons and "Open in Browser" action options.
- **✉️ In-App Email Composer:** Composed messages directly within the app with subject and body fields, backed by a dedicated Back button.
- **📤 In-App Contact Sharing & vCard:** Preview vCard contact details, share via system share sheets, or copy directly to clipboard with real-time Snackbar notifications.
- **💬 Real-time Feedback:** Snackbar notifications confirming user actions.
- **🔄 Robust Lifecycle & Rotation Handling:** Fully tested across portrait and landscape orientations with state retention.
- **♿ Accessibility Compliant:** Fully labeled interactive elements and touch targets designed for screen readers.

---

## 📸 App Screenshots & UI Showcase

| Main Business Card | Sessionize Profile | X (Twitter) Profile |
| :---: | :---: | :---: |
| *Dark theme card with sessionize, social links, email, and share actions* | *In-app WebView with top app bar, back button, and browser launcher* | *In-app WebView rendering @JustJessZA profile* |

| GitHub Profile | In-App Email Composer | In-App Share Contact |
| :---: | :---: | :---: |
| *In-app WebView rendering GitHub repository & bio* | *Compose email with subject, message, and back button* | *vCard preview, system share, and clipboard copy* |

---

## 🛠️ Tech Stack & Architecture

- **Language:** [Kotlin](https://kotlinlang.org) 2.x
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io)
- **Icons:** Material Icons Extended (`androidx.compose.material:material-icons-extended`)
- **Lifecycle & Core:** AndroidX Activity Compose, Lifecycle KTX
- **Testing Frameworks:**
  - **Unit Testing:** JUnit 4 (`testImplementation`)
  - **UI Instrumentation Testing:** Compose UI Test JUnit4 (`androidTestImplementation`)
  - **Intent Verification:** Espresso Intents (`espresso-intents`)

---

## 📂 Project Structure

```tree
com.starchild13.businesscard/
├── MainActivity.kt           # App entry point & activity host
├── BusinessCardScreen.kt     # Core composable screens, layout hierarchies & intent handlers
└── ui/
    └── theme/
        ├── Color.kt          # Color palette definitions
        ├── Shape.kt          # Material shapes
        ├── Theme.kt          # Material 3 theme wrapper
        └── Type.kt           # Typography definitions
```

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Koala / Ladybug or newer recommended)
- **Android SDK Platform 37**
- **Gradle 8.x+**

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Starchild13/BusinessCardApp.git
   ```

2. **Open the project:**
   - Open Android Studio and select **Open an Existing Project**, navigating to the cloned directory.

3. **Sync Gradle:**
   - Allow Android Studio to download dependencies and sync Gradle files.

4. **Run the Application:**
   - Connect a physical Android device with USB debugging enabled or launch an Android Emulator (API 24+).
   - Click the **Run 'app'** ▶️ button in Android Studio.

---

## 💡 Usage & Customization

To personalize the business card for your own profile, modify the parameters inside [`BusinessCardScreen.kt`](file:///app/src/main/java/com/starchild13/businesscard/BusinessCardScreen.kt):

```kotlin
ProfileCard(
    name = "Your Name",
    role = "Your Professional Title",
    image = painterResource(R.drawable.your_logo),
    snackbarHostState = snackbarHostState,
    scope = scope,
    onIconClick = { type ->
        // Handle your custom link clicks here
    }
)
```

You can update social URLs and contact actions inside the `onIconClick` lambda within `BusinessCardScreen()`.

---

## 🧪 Testing

The project includes a comprehensive suite of unit and UI tests located in `app/src/androidTest/java/com/starchild13/businesscard/BusinessCardUITest.kt`.

To run tests via Gradle CLI:
- **Unit Tests:**
  ```bash
  ./gradlew app:testDebugUnitTest
  ```
- **Instrumentation / UI Tests:**
  ```bash
  ./gradlew app:connectedDebugAndroidTest
  ```

---

## 📜 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## 👤 Author

- **Jessica Randall** ([@Starchild13](https://github.com/Starchild13))
