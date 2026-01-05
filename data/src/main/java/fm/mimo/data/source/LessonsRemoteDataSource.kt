package fm.mimo.data.source

import fm.mimo.data.remote.LessonsApi
import fm.mimo.data.remote.response.ApiLesson
import javax.inject.Inject

class LessonsRemoteDataSource @Inject constructor(val api: LessonsApi): LessonsDataSource {
    override suspend fun fetchLessons(): List<ApiLesson> {
        return api.fetchLessons().lessons
    }
}
