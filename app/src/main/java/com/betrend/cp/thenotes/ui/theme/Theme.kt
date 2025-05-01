package com.betrend.cp.thenotes.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Graffit,
    onPrimary = GraffitDD,
    secondary = GraffitD,
    onSecondary = GraffitL,
    tertiary = GraffitL,
    onTertiary = Graffit,
    background = GraffitL,
    onBackground = YellowNoteLL,
    surface = GraffitL,
    onSurface = YellowNote,
    surfaceVariant = GraffitDD,
    onSurfaceVariant = YellowNoteL,
)

private val LightColorScheme = lightColorScheme(
    primary = YellowNote,
    onPrimary = YellowNoteDD,
    onPrimaryContainer = YellowNoteD,
    secondary = YellowNoteD,
    onSecondary = YellowNoteL,
    tertiary = YellowNoteL,
    onTertiary = Graffit,
    background = YellowNoteLL,
    onBackground = GraffitD,
    surface = YellowNoteLL,
    onSurface = Graffit,
    surfaceVariant = YellowNoteDD,
    onSurfaceVariant = GraffitL,
    onErrorContainer = NoteError,

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

    val colors = if (!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    if (!view.isInEditMode){
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.secondary.toArgb()
            window.navigationBarColor = colors.secondary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}