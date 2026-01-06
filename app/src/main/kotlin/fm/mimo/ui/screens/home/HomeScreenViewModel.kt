package fm.mimo.ui.screens.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fm.mimo.DispatcherProvider
import fm.mimo.R
import fm.mimo.domain.model.Lesson
import fm.mimo.domain.usecase.GetLessonsUseCase
import fm.mimo.lib.mvi.BaseViewModel
import fm.mimo.ui.UiText
import fm.mimo.ui.UiText.DynamicString
import fm.mimo.ui.UiText.StringResource
import fm.mimo.ui.screens.home.Action.Initialize
import fm.mimo.ui.screens.home.Action.InputValueChange
import fm.mimo.ui.screens.home.Action.PrimaryButtonTap
import jakarta.inject.Inject
import kotlinx.coroutines.launch

abstract class HomeScreenViewModel : BaseViewModel<State, Action, Effect>()

@HiltViewModel
class HomeScreenViewModelImpl @Inject constructor(
    private val useCase: GetLessonsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : HomeScreenViewModel() {
    private var lessons: List<Lesson> = emptyList()

    override fun initState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Initialize -> init()
            is InputValueChange -> onInputValueChanged(action.lessonId, action.newText)
            PrimaryButtonTap -> onPrimaryButtonTapped()
        }
    }

    private fun init() {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch(dispatcherProvider.io()) {
        lessons = useCase.retrieveLessons()
        val firstLesson = lessons.firstOrNull()
        updateLessonState(firstLesson)
    }

    private fun onPrimaryButtonTapped() {
        val currentLessonIndex = lessons.indexOfFirst { it.id == uiState.value.currentLessonId }
        updateLessonState(lessons.getOrNull(currentLessonIndex + 1))
    }

    private fun onInputValueChanged(id: Int, newValue: String) {
        submitState {
            copy(contentItems = contentItems.map {
                if (it is ContentItem.ContentWithInput && it.lessonId == id) it.copy(
                    currentInputText = DynamicString(newValue)
                )
                else it
            }, buttonEnabled = newValue.isNotBlank())
        }
    }

    private fun updateLessonState(lesson: Lesson?) {
        if (lesson == null) return

        submitState {
            copy(
                title = StringResource(
                    R.string.home_title_label,
                    arrayOf(lesson.id)
                ),
                contentItems = listOf(lesson.buildItems()),
                currentLessonId = lesson.id,
                buttonLabel = StringResource(R.string.home_button_next_label),
                buttonEnabled = lesson.hasInput.not(),
            )
        }
    }

    private fun Lesson.buildItems(): ContentItem {
        return when {
            hasInput -> {
                val inputText = computeInputText()
                ContentItem.ContentWithInput(
                    lessonId = id,
                    leadingText = computeLeadingText(),
                    trailingText = computeTrailingText(),
                    inputLength = inputText.length,
                    expectedInputText = DynamicString(inputText),
                    outlineColor = retrieveOutlineColor()
                )
            }

            else -> ContentItem.ContentWithoutInput(
                lessonId = id,
                text = DynamicString(computeSolutionText()),
            )
        }
    }

    private fun Lesson.computeLeadingText(): UiText {
        val solutionText = computeSolutionText()
        val expectedInputText = computeInputText()
        val leadingText = solutionText.substringBefore(expectedInputText)
        return DynamicString(leadingText)
    }

    private fun Lesson.computeTrailingText(): UiText {
        val solutionText = computeSolutionText()
        val expectedInputText = computeInputText()
        val trailingText = solutionText.substringAfter(expectedInputText)
        return DynamicString(trailingText)
    }

    private fun Lesson.computeInputText(): String {
        val solutionText = computeSolutionText()
        val input = input!!
        val expectedInputText = solutionText.substring(input.startIndex, input.endIndex)
        return expectedInputText
    }

    private fun Lesson.computeSolutionText(): String {
        return fields.joinToString("") { it.text }
    }

    private fun Lesson.retrieveOutlineColor(): String? {
        val expectedInputText = computeInputText()
        val field = fields.firstOrNull { it.text == expectedInputText }
        return field?.color
    }
}
