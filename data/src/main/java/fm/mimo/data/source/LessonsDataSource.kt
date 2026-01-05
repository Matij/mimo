package fm.mimo.data.source

import fm.mimo.data.remote.response.ApiLesson

interface LessonsDataSource {
    suspend fun fetchLessons(): List<ApiLesson>
}
