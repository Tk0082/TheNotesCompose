package com.betrend.cp.thenotes.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GraffitD,
    secondary = Graffit,
    tertiary = GraffitL,
    background = Graffit,
    onBackground = GraffitDD,
    onPrimary = GraffitL,
    onSecondary = GraffitLL
)

private val LightColorScheme = lightColorScheme(
    primary = YellowNote,
    onPrimary = YellowNoteDD,
    onPrimaryContainer = YellowNoteD,
    secondary = YellowNoteD,
    tertiary = YellowNoteL,
    background = YellowNoteLL,
    onBackground = YellowNoteD,
    surface = YellowNoteLL,
    surfaceVariant = YellowNoteDD,
    onSurface = YellowNoteD,
    onSurfaceVariant = YellowNoteLL,
    onSecondary = YellowNoteL,
    onErrorContainer = NoteError

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


@Composable
fun TheNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = LightColorScheme.secondary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = LightColorScheme.primary.isSpecified
        }
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}