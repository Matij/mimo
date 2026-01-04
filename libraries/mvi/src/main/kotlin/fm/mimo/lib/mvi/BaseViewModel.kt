package fm.mimo.lib.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

interface UiAction

interface UiEffect

abstract class BaseViewModel<State : UiState, Action : UiAction, Effect : UiEffect> : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<State> by lazy { MutableStateFlow(initState()) }
    val uiState: StateFlow<State> = _uiStateFlow

    private val actionFlow: MutableSharedFlow<Action> = MutableSharedFlow()

    private val _effectFlow = Channel<Effect>()
    val effect = _effectFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
    }

    abstract fun initState(): State
    abstract fun handleAction(action: Action)

    fun submitState(reducer: State.() -> State) {
        _uiStateFlow.update { it.reducer() }
    }

    fun submitAction(action: Action) = viewModelScope.launch {
        actionFlow.emit(action)
    }

    fun submitEffect(effect: Effect) = viewModelScope.launch {
        _effectFlow.send(effect)
    }
}
