package fm.mimo.data.remote.response

import fm.mimo.domain.model.Input

data class ApiInput(
    val startIndex: Int,
    val endIndex: Int,
)

fun ApiInput.toDomain(): Input {
    return Input(startIndex = startIndex, endIndex = endIndex)
}
