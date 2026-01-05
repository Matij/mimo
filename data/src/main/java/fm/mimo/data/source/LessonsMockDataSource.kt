package fm.mimo.data.source

import fm.mimo.data.remote.response.ApiLesson
import fm.mimo.data.remote.response.ApiLessons
import jakarta.inject.Inject

class LessonsMockDataSource @Inject constructor(
    private val dataSourceFileReader: DataSourceFileReader
): LessonsDataSource {
    override suspend fun fetchLessons(): List<ApiLesson> {
        val fileName = "mock/get_lessons_success.json"
        val text = dataSourceFileReader.readFromFile(fileName)
        val result = dataSourceFileReader.fromJson<ApiLessons>(text)
        return result.lessons
    }

}