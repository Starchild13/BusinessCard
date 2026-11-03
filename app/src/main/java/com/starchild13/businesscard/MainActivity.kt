/**
 * Copyright (c) 2023-2025 Jessica Randall (Starchild13)
 * Licensed under the MIT License.
 *
 * MainActivity.kt serves as the primary entry point for the Business Card Android application.
 * It initializes the component lifecycle and hosts the Jetpack Compose UI hierarchy.
 */
package com.starchild13.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.starchild13.businesscard.ui.theme.BusinessCardTheme

/**
 * Main activity for the application, inheriting from [ComponentActivity].
 * Responsible for setting up the Compose content view with the custom [BusinessCardTheme].
 */
class MainActivity : ComponentActivity() {

    /**
     * Lifecycle callback invoked when the activity is first created.
     * Sets the Jetpack Compose content tree containing [BusinessCardTheme] and [BusinessCardScreen].
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                BusinessCardScreen()
            }
        }
    }
}
