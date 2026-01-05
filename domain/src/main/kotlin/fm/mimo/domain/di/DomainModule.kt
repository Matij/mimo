package fm.mimo.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import fm.mimo.domain.repository.LessonsRepository
import fm.mimo.domain.usecase.GetLessonsUseCase

@Module
@InstallIn(ActivityComponent::class)
object DomainModule {
    @Provides
    fun provideGetLessonsUseCase(
        repository: LessonsRepository,
    ) = GetLessonsUseCase(lessonsRepository = repository)
}
