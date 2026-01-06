package fm.mimo.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import fm.mimo.ui.components.FixedWidthOutlinedTextField
import fm.mimo.ui.screens.home.Action.Initialize
import fm.mimo.ui.screens.home.Action.PrimaryButtonTap
import fm.mimo.ui.screens.home.ContentItem.ContentWithInput
import fm.mimo.ui.screens.home.ContentItem.ContentWithoutInput

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModelImpl = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(Initialize)
    }

    val state by viewModel.uiState.collectAsState()
    with(state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 200.dp),
                text = title.asString(),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(48.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(items = state.contentItems) { item ->
                    when (item) {
                        is ContentWithoutInput -> Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item.text.asString(),
                        )

                        is ContentWithInput -> ContentRow(content = item) {
                            viewModel.submitAction(
                                Action.InputValueChange(
                                    lessonId = item.lessonId,
                                    newText = it,
                                )
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(48.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.submitAction(PrimaryButtonTap) },
                enabled = buttonEnabled,
            ) {
                Text(buttonLabel.asString())
            }
        }
    }
}

@Composable
private fun ContentRow(
    content: ContentWithInput,
    onInputValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = content.leadingText.asString(),
        )
        FixedWidthOutlinedTextField(
            value = content.currentInputText.asString(),
            onValueChange = onInputValueChange,
            maxChars = content.inputLength,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = content.trailingText.asString(),
        )
    }
}