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
import fm.mimo.ui.screens.home.ContentItem.ContentWithInput
import fm.mimo.ui.screens.home.Effect.LessonsDone
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
        val contentWithInputOnScreen = uiState.value.contentItems
            .filterIsInstance<ContentWithInput>()
            .firstOrNull { it.lessonId == uiState.value.currentLessonId }

        val correctAnswer = validateAnswer(contentWithInputOnScreen)

        if (contentWithInputOnScreen == null || correctAnswer) {
            val currentLessonIndex = lessons.indexOfFirst { it.id == uiState.value.currentLessonId }
            val nextLesson = lessons.getOrNull(currentLessonIndex + 1)
            updateLessonState(nextLesson)
            if (nextLesson == null) submitEffect(LessonsDone)
        } else {
            submitState { copy(errorMessage = StringResource(R.string.error_message_incorrect_answer)) }
        }
    }

    private fun onInputValueChanged(id: Int, newValue: String) {
        submitState {
            copy(
                contentItems = contentItems.map {
                    if (it is ContentWithInput && it.lessonId == id) it.copy(
                        currentInputText = newValue,
                    )
                    else it
                },
                buttonEnabled = newValue.isNotBlank(),
                errorMessage = if (newValue.isBlank()) null else errorMessage,
            )
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
                ContentWithInput(
                    lessonId = id,
                    leadingText = computeLeadingText(),
                    trailingText = computeTrailingText(),
                    inputLength = inputText.length,
                    expectedInputText = inputText,
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

    private fun validateAnswer(contentItemContentWithInput: ContentWithInput?): Boolean {
        if (contentItemContentWithInput == null) return false
        val expectedAnswer = contentItemContentWithInput.expectedInputText
        val currentInputText = contentItemContentWithInput.currentInputText
        return currentInputText == expectedAnswer
    }
}
