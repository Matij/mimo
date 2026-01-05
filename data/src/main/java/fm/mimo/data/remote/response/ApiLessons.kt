package fm.mimo.data.remote.response

import fm.mimo.domain.model.Lesson

data class ApiLessons(val lessons: List<ApiLesson>)

data class ApiLesson(
    val id: Int,
    val content: List<ApiField>,
    val input: ApiInput?,
)

fun List<ApiLesson>.toDomainLessons(): List<Lesson> {
    return map { it.toDomain() }
}

fun ApiLesson.toDomain(): Lesson {
    return Lesson(
        id = id,
        input = input?.toDomain(),
        fields = content.toDomainFields(),
        hasInput = input != null,
    )
}
