package fm.mimo.domain.model

data class Lesson(
    val id: Int,
    val fields: List<Field>,
    val input: Input?,
    val hasInput: Boolean,
)
