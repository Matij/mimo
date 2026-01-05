package fm.mimo.domain.usecase

import fm.mimo.domain.repository.LessonsRepository
import jakarta.inject.Inject

class GetLessonsUseCase @Inject constructor(private val lessonsRepository: LessonsRepository) {
    suspend fun retrieveLessons() = lessonsRepository.retrieveLessons().sortedBy { it.id }
}
