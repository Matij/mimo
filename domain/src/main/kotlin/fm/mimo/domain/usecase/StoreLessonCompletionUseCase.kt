package fm.mimo.domain.usecase

import fm.mimo.domain.repository.LessonsRepository
import javax.inject.Inject

class StoreLessonCompletionUseCase @Inject constructor(
    private val lessonsRepository: LessonsRepository
) {
    suspend operator fun invoke(lessonId: Int, startedAt: Long, completedAt: Long) {
        lessonsRepository.storeLessonCompletion(lessonId, startedAt, completedAt)
    }
}
