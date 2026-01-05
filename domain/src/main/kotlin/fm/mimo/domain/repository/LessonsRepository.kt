package fm.mimo.domain.repository

import fm.mimo.domain.model.Lesson

interface LessonsRepository {
    suspend fun retrieveLessons(): List<Lesson>
    suspend fun retrieveLesson(id: Int): Lesson
}
