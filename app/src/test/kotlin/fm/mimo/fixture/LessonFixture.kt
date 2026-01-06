package fm.mimo.fixture

import fm.mimo.domain.model.Field
import fm.mimo.domain.model.Input
import fm.mimo.domain.model.Lesson

fun provideLessons() = listOf(
    Lesson(
        id = 1,
        fields = provideFields(),
        hasInput = true,
        input = Input(
            startIndex = 6,
            endIndex = 11,
        ),
    ),
    Lesson(
        id = 2,
        fields = listOf(Field("#FFFFFFFF", text = "This is just a test lesson")),
        hasInput = false,
        input = null,
    ),
)

private fun provideFields() = listOf(
    Field(color = "#FF000000", text = "Hello "),
    Field(color = "#FFABCDEF", text = "World"),
    Field(color = "#FF000000", text = "!"),
)