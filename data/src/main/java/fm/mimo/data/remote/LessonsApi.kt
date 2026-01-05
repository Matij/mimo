package fm.mimo.data.remote

import fm.mimo.data.remote.response.ApiLessons
import retrofit2.http.GET

interface LessonsApi {
    @GET("lessons")
    suspend fun fetchLessons(): ApiLessons
}
