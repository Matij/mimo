package fm.mimo.fixture

import fm.mimo.ui.UiText.DynamicString
import fm.mimo.ui.screens.home.ContentItem.ContentWithInput
import fm.mimo.ui.screens.home.ContentItem.ContentWithoutInput

fun provideContentItems() = listOf(
    ContentWithInput(
        lessonId = 1,
        leadingText = DynamicString("Hello "),
        leadingTextColor = "#FF000000",
        expectedInputText = "World",
        trailingText = DynamicString("!"),
        trailingTextColor = "#FF000000",
        inputLength = 5,
        outlineColor = "#FFABCDEF"
    ),
    ContentWithoutInput(
        lessonId = 2,
        text = DynamicString("This is just a test lesson"),
        textColor = "#FFFFFFFF",
    ),
)