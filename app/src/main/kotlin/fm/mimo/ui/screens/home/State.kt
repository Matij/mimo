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
    val buttonLabel: UiText = StringResource(R.string.home_button_run_label),
    val buttonEnabled: Boolean = true,
) : UiState

sealed interface ContentItem {
    data class ContentWithInput(
        val lessonId: Int,
        val leadingText: UiText,
        val trailingText: UiText,
        val currentInputText: UiText = DynamicString(""),
        val expectedInputText: UiText,
        val inputLength: Int,
    ) : ContentItem

    data class ContentWithoutInput(
        val lessonId: Int,
        val text: UiText,
    ) : ContentItem
}

sealed interface Action : UiAction {
    data object Initialize : Action
    data class InputValueChange(val lessonId: Int, val newText: String) : Action
    data object PrimaryButtonTap : Action
}

sealed interface Effect : UiEffect {
    data object Error : Effect
}
