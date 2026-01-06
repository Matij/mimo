package fm.mimo.data.repository

import fm.mimo.data.local.dao.LessonCompletionEventDao
import fm.mimo.data.local.model.LessonCompletionEvent
import fm.mimo.data.remote.response.toDomainLessons
import fm.mimo.data.source.LessonsDataSource
import fm.mimo.domain.model.Lesson
import fm.mimo.domain.repository.LessonsRepository
import javax.inject.Inject

class LessonsRepositoryImpl @Inject constructor(
    private val remoteDataSource: LessonsDataSource,
    private val localDataSource: LessonCompletionEventDao,
): LessonsRepository {
    override suspend fun retrieveLessons(): List<Lesson> {
        return remoteDataSource.fetchLessons().toDomainLessons()
    }

    override suspend fun retrieveLesson(id: Int): Lesson {
        TODO("Not yet implemented")
    }

    override suspend fun storeLessonCompletion(lessonId: Int, startedAt: Long, completedAt: Long) {
        val event = LessonCompletionEvent(
            lessonId = lessonId,
            startedAt = startedAt,
            completedAt = completedAt,
        )
        localDataSource.insert(event)
    }
}
