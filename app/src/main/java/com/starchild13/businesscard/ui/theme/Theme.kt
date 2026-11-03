/**
 * Copyright (c) 2023-2025 Jessica Randall (Starchild13)
 * Licensed under the MIT License.
 *
 * Theme.kt defines the Material 3 color schemes and custom typography theme wrapper
 * for the Business Card application supporting both light and dark modes.
 */
package com.starchild13.businesscard.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Dark color palette configuration optimized for high-contrast viewing with dark backgrounds
 * and vibrant green brand accents.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

/**
 * Light color palette configuration maintaining clean contrast across light surfaces.
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFF03DAC5),
    tertiary = Color(0xFF3700B3),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

/**
 * Custom Material 3 theme composable for the Business Card application.
 * Automatically switches between light and dark color schemes based on system settings
 * or explicit overrides.
 *
 * @param darkTheme Whether to use the dark color scheme (defaults to system setting).
 * @param content Composable lambda representing the UI hierarchy nested within this theme.
 */
@Composable
fun BusinessCardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
