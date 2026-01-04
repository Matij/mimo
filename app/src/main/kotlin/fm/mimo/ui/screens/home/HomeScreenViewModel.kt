package fm.mimo.ui.screens.home

import fm.mimo.lib.mvi.BaseViewModel
import fm.mimo.ui.screens.home.Action.Initialize

class HomeScreenViewModel : BaseViewModel<State, Action, Effect>() {
    override fun initState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Initialize -> TODO()
        }
    }
}