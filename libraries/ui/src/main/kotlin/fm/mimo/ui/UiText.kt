package fm.mimo.ui

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource

/**
 * A representation of user-facing text that can be converted to an actual string from a composable
 * or view. This allows for calls to stringResource or context.getString with the appropriate
 * context for a given fragment or screen.
 *
 * The UiText subclasses StringResource and PluralResource can take arguments. Those arguments can
 * include:
 *  - Another UiText
 *  - Any primitive type that can be used in regular string formatting
 *  @see <a href=https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#syntax>Java string format options</a>
 */
sealed class UiText {

    /**
     * Given an array that may contain UiText values, convert those values to strings,
     * which can then be used as string arguments in a parent PluralResource or StringResource
     * UiText object
     */
    @Composable
    private fun Array<Any>.expand() =
        map { if (it is UiText) it.asString() else it }.toTypedArray()

    private fun Array<Any>.expand(context: Context) =
        map { if (it is UiText) it.asString(context) else it }.toTypedArray()

    /**
     * Dynamic string simply wraps an existing string
     */
    data class DynamicString(val value: String) : UiText()

    /**
     * StringResource takes
     * @param resId string resource id and
     * @param args an array of arguments, which should contain the same number of arguments
     * defined in the string resource. This array can contain UiText values
     */
    data class StringResource(
        @field:StringRes val resId: Int,
        val args: Array<Any> = emptyArray(),
    ) : UiText() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringResource

            if (resId != other.resId) return false
            return args.contentEquals(other.args)
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    /**
     * PluralResource takes
     * @param resId plural string resource id
     * @param count determines which plural string value to use
     * @param args an array of arguments, which should contain the same number of arguments defined
     * in the plural string resource. If the count is one of the string arguments, it must be
     * included in the args array. This array can contain UiText values.
     */
    data class PluralResource(
        @field:PluralsRes val resId: Int,
        val count: Int,
        val args: Array<Any> = emptyArray(),
    ) : UiText() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PluralResource

            if (resId != other.resId) return false
            return args.contentEquals(other.args)
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    /**
     * This can be called from a composable to generate a user-facing string. This is where any
     * UIText arguments are expanded to strings themselves
     */
    @Composable
    fun asString(defaultValue: String = ""): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> {
                if (resId == 0) {
                    defaultValue
                } else {
                    stringResource(resId, *args.expand())
                }
            }

            is PluralResource -> {
                if (resId == 0) {
                    defaultValue
                } else {
                    pluralStringResource(id = resId, count = count, *args.expand())
                }
            }
        }
    }

    /**
     * This can be called from a view with a context to generate a user-facing string. This is
     * where any UIText arguments are expanded to strings themselves
     */
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args.expand(context))
            is PluralResource -> context.resources.getQuantityString(
                resId,
                count,
                *args.expand(context)
            )
        }
    }
}
