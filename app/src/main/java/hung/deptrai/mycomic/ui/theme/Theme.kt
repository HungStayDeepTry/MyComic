package hung.deptrai.mycomic.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF6740),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB23C1F),
    onPrimaryContainer = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFFFFA07A),

    secondary = Color(0xFFFFC107),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFB28704),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiary = Color(0xFF00BCD4),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFF008BA3),
    onTertiaryContainer = Color(0xFFFFFFFF),

    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF262626),
    onSurfaceVariant = Color(0xFFC8C8C8),
    surfaceTint = Color(0xFFFF6740),
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF000000),

    error = Color(0xFFF44336),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFB71C1C),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFF616161),
    outlineVariant = Color(0xFF424242),
    scrim = Color(0xFF808080)
)


// ☀️ MangaDex Light Theme (giữ đồng nhất hoặc có thể sáng hơn nếu thích)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6740),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFCCBC),
    onPrimaryContainer = Color(0xFF000000),
    inversePrimary = Color(0xFFB23C1F),

    secondary = Color(0xFFFFC107),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFFECB3),
    onSecondaryContainer = Color(0xFF000000),

    tertiary = Color(0xFF00BCD4),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFB2EBF2),
    onTertiaryContainer = Color(0xFF000000),

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF424242),
    surfaceTint = Color(0xFFFF6740),
    inverseSurface = Color(0xFF121212),
    inverseOnSurface = Color(0xFFFFFFFF),

    error = Color(0xFFF44336),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFCDD2),
    onErrorContainer = Color(0xFF000000),

    outline = Color(0xFF9E9E9E),
    outlineVariant = Color(0xFFBDBDBE).copy(alpha = 0.3f),
    scrim = Color(0xFF000000)
)

@Composable
fun MyComicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
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