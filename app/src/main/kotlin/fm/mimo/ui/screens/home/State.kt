package fm.mimo.ui.screens.home

import fm.mimo.R
import fm.mimo.lib.mvi.UiAction
import fm.mimo.lib.mvi.UiEffect
import fm.mimo.lib.mvi.UiState
import fm.mimo.ui.UiText
import fm.mimo.ui.UiText.DynamicString
import fm.mimo.ui.UiText.StringResource

data class State(
    val title: UiText = DynamicString(""),
    val contentItems: List<ContentItem> = emptyList(),
    val currentLessonId: Int? = null,
    val errorMessage: UiText? = null,
    val emptyStateMessage: UiText? = null,
    val showEmptyState: Boolean = false,
    val buttonLabel: UiText = StringResource(R.string.home_button_run_label),
    val buttonEnabled: Boolean = true,
) : UiState

sealed interface ContentItem {
    data class ContentWithInput(
        val lessonId: Int,
        val leadingText: UiText,
        val leadingTextColor: String?,
        val trailingText: UiText,
        val trailingTextColor: String?,
        val currentInputText: String = "",
        val expectedInputText: String,
        val inputLength: Int,
        val outlineColor: String?,
    ) : ContentItem

    data class ContentWithoutInput(
        val lessonId: Int,
        val text: UiText,
        val textColor: String?,
    ) : ContentItem
}

sealed interface Action : UiAction {
    data object Initialize : Action
    data class InputValueChange(val lessonId: Int, val newText: String) : Action
    data object PrimaryButtonTap : Action
}

sealed interface Effect : UiEffect {
    data object LessonsDone : Effect
}

data class SplitText(
    val leading: String,
    val trailing: String
)
