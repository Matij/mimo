package fm.mimo.ui.screens.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fm.mimo.DispatcherProvider
import fm.mimo.domain.usecase.GetLessonsUseCase
import fm.mimo.lib.mvi.BaseViewModel
import fm.mimo.ui.screens.home.Action.Initialize
import jakarta.inject.Inject
import kotlinx.coroutines.launch

abstract class HomeScreenViewModel : BaseViewModel<State, Action, Effect>()

@HiltViewModel
class HomeScreenViewModelImpl @Inject constructor(
    private val useCase: GetLessonsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : HomeScreenViewModel() {
    override fun initState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Initialize -> init()
        }
    }

    private fun init() {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch(dispatcherProvider.io()) {
        useCase.retrieveLessons()
    }
}
