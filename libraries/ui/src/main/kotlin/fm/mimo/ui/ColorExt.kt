package fm.mimo.ui

import androidx.compose.ui.graphics.Color

fun String.fromHex(): Color {
    val hex = this
    val cleaned = hex.removePrefix("#")

    require(cleaned.length == 6 || cleaned.length == 8) {
        "Hex color must be in the form #RRGGBB or #AARRGGBB"
    }

    val colorLong = cleaned.toLong(16)

    return if (cleaned.length == 6) {
        Color(
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f,
            alpha = 1f
        )
    } else {
        Color(
            alpha = ((colorLong shr 24) and 0xFF) / 255f,
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f
        )
    }
}
