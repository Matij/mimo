package fm.mimo.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FixedWidthOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    maxChars: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    val width = rememberTextFieldWidthForChars(
        maxChars = maxChars,
        textStyle = textStyle,
        horizontalPadding = TextFieldDefaultsPadding.Outlined
    )

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxChars) onValueChange(it)
        },
        modifier = modifier.width(width),
        textStyle = textStyle,
        singleLine = true
    )
}

@Composable
fun rememberTextFieldWidthForChars(
    maxChars: Int,
    textStyle: TextStyle,
    horizontalPadding: Dp
): Dp {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    return remember(maxChars, textStyle, horizontalPadding) {
        val referenceText = "W".repeat(maxChars)

        val result = textMeasurer.measure(
            text = referenceText,
            style = textStyle
        )

        with(density) {
            result.size.width.toDp() + horizontalPadding * 2
        }
    }
}

object TextFieldDefaultsPadding {
    val Filled = 16.dp       // TextField
    val Outlined = 12.dp     // OutlinedTextField
}

