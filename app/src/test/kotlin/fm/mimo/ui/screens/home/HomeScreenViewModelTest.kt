package fm.mimo.ui.screens.home

import app.cash.turbine.test
import fm.mimo.R
import fm.mimo.common.BaseTest
import fm.mimo.domain.usecase.GetLessonsUseCase
import fm.mimo.domain.usecase.StoreLessonCompletionUseCase
import fm.mimo.fixture.provideContentItems
import fm.mimo.fixture.provideLessons
import fm.mimo.ui.UiText.StringResource
import fm.mimo.ui.screens.home.Action.Initialize
import fm.mimo.ui.screens.home.Action.InputValueChange
import fm.mimo.ui.screens.home.Action.PrimaryButtonTap
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HomeScreenViewModelTest : BaseTest() {
    private lateinit var viewModel: HomeScreenViewModel

    private val mockGetLessonsUseCase: GetLessonsUseCase = mockk(relaxed = true)
    private val mockStoreLessonCompletionUseCase: StoreLessonCompletionUseCase =
        mockk(relaxed = true)

    @Before
    override fun setup() {
        super.setup()
        viewModel = HomeScreenViewModelImpl(
            getLessonsUseCase = mockGetLessonsUseCase,
            storeLessonCompletionUseCase = mockStoreLessonCompletionUseCase,
            dispatcherProvider = testDispatcherProvider,
        )
    }

    @Test
    fun `GIVEN retrieveLessons fails, WHEN Initialize, THEN show empty state`() = runTest {
        coEvery { mockGetLessonsUseCase.retrieveLessons() } returns emptyList()

        viewModel.handleAction(Initialize)

        viewModel.uiState.test {
            val state = awaitItem()
            assert(state.emptyStateMessage != null)
        }
    }

    @Test
    fun `GIVEN retrieveLessons succeeds and lesson with input available, WHEN Initialize, THEN show lesson`() =
        runTest {
            val lessons = provideLessons()
            val firstLessonId = lessons.first().id

            coEvery { mockGetLessonsUseCase.retrieveLessons() } returns lessons

            viewModel.handleAction(Initialize)

            val expectedState = State(
                title = StringResource(R.string.home_title_label, arrayOf(firstLessonId)),
                contentItems = listOf(provideContentItems().first()),
                currentLessonId = firstLessonId,
                buttonLabel = StringResource(R.string.home_button_next_label),
                buttonEnabled = false,
                emptyStateMessage = StringResource(R.string.home_screen_empty_state_message),
            )
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(expectedState, /*actual =*/ state)
            }
        }

    @Test
    fun `GIVEN lesson without content available, WHEN InputValueChange with wrong value, THEN show error`() =
        runTest {
            val lessons = provideLessons()
            val testLessonId = lessons.first().id
            val testInputText = "abc"

            coEvery { mockGetLessonsUseCase.retrieveLessons() } returns lessons

            viewModel.handleAction(Initialize)
            viewModel.handleAction(
                InputValueChange(
                    lessonId = testLessonId,
                    newText = testInputText
                )
            )
            viewModel.handleAction(PrimaryButtonTap)

            val expectedState = State(
                title = StringResource(R.string.home_title_label, arrayOf(testLessonId)),
                contentItems = listOf(
                    (provideContentItems().first() as ContentItem.ContentWithInput).copy(
                        currentInputText = testInputText
                    )
                ),
                currentLessonId = testLessonId,
                buttonLabel = StringResource(R.string.home_button_next_label),
                buttonEnabled = true,
                emptyStateMessage = StringResource(R.string.home_screen_empty_state_message),
                errorMessage = StringResource(R.string.error_message_incorrect_answer)
            )
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(expectedState, /*actual =*/ state)
            }
        }

    @Test
    fun `GIVEN lesson without content available, WHEN InputValueChange with correct value, THEN show next lesson`() =
        runTest {
            val lessons = provideLessons()
            val testLessonId = lessons.last().id

            coEvery { mockGetLessonsUseCase.retrieveLessons() } returns lessons

            viewModel.handleAction(Initialize)
            viewModel.handleAction(InputValueChange(lessonId = 1, newText = "World"))
            viewModel.handleAction(PrimaryButtonTap)

            val expectedState = State(
                title = StringResource(R.string.home_title_label, arrayOf(testLessonId)),
                contentItems = listOf(provideContentItems().last()),
                currentLessonId = testLessonId,
                buttonLabel = StringResource(R.string.home_button_next_label),
                buttonEnabled = true,
                emptyStateMessage = StringResource(R.string.home_screen_empty_state_message),
            )
            viewModel.uiState.test {
                val state = awaitItem()
                assertEquals(expectedState, /*actual =*/ state)
            }
        }

}