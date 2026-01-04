package fm.mimo.ui.screens.home

import fm.mimo.R
import fm.mimo.lib.mvi.UiAction
import fm.mimo.lib.mvi.UiEffect
import fm.mimo.lib.mvi.UiState
import fm.mimo.ui.UiText
import fm.mimo.ui.UiText.StringResource

data class State(
    val title: UiText = StringResource(R.string.home_screen_title),
    val buttonLabel: UiText = StringResource(R.string.home_screen_button_label),
    val loading: Boolean = false,
) : UiState

sealed class Action : UiAction {
    data object Initialize : Action()
}

sealed class Effect : UiEffect {
    data object Error : Effect()
}
