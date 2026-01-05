package fm.mimo.data.remote.response

import fm.mimo.domain.model.Field

data class ApiField(
    val color: String,
    val text: String,
)

fun List<ApiField>.toDomainFields(): List<Field> {
    return map { it.toDomain() }
}

fun ApiField.toDomain(): Field {
    return Field(color = color, text = text)
}
