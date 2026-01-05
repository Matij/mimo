package fm.mimo.data.repository

import fm.mimo.data.remote.response.toDomainLessons
import fm.mimo.data.source.LessonsDataSource
import fm.mimo.domain.model.Lesson
import fm.mimo.domain.repository.LessonsRepository
import jakarta.inject.Inject

class LessonsRepositoryImpl @Inject constructor(
    private val dataSource: LessonsDataSource,
): LessonsRepository {
    override suspend fun retrieveLessons(): List<Lesson> {
        return dataSource.fetchLessons().toDomainLessons()
    }

    override suspend fun retrieveLesson(id: Int): Lesson {
        TODO("Not yet implemented")
    }
}
