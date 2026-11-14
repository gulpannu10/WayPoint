package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SerenePrimary,
    onPrimary = SereneOnPrimary,
    primaryContainer = SerenePrimaryContainer,
    onPrimaryContainer = SereneOnPrimaryContainer,
    secondary = SereneSecondary,
    onSecondary = SereneOnSecondary,
    secondaryContainer = SereneSecondaryContainer,
    onSecondaryContainer = SereneOnSecondaryContainer,
    tertiary = SereneTertiary,
    onTertiary = SereneOnTertiary,
    tertiaryContainer = SereneTertiaryContainer,
    background = SereneBackground,
    onBackground = SereneOnSurface,
    surface = SereneSurface,
    onSurface = SereneOnSurface,
    surfaceVariant = SereneSurfaceContainerHigh,
    onSurfaceVariant = SereneOnSurfaceVariant,
    outline = SereneOutline,
    outlineVariant = SereneOutlineVariant,
    error = SereneError,
    errorContainer = SereneErrorContainer,
    onError = SereneOnError,
    onErrorContainer = SereneOnErrorContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = SerenePrimaryContainer,
    onPrimary = SereneOnPrimary,
    primaryContainer = SerenePrimary,
    onPrimaryContainer = SereneOnPrimaryContainer,
    secondary = SereneSecondaryContainer,
    onSecondary = SereneOnSecondaryContainer,
    background = SereneOnSurface,
    surface = SereneOnSurface,
    onSurface = SereneSurfaceContainerLowest,
    onBackground = SereneSurfaceContainerLowest
)

@Composable
fun WaypointTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WaypointTypography,
        content = content
    )
}
