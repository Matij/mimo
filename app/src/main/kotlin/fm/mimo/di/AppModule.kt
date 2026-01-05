package fm.mimo.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fm.mimo.BuildConfig
import fm.mimo.di_android.ApiBaseUrl

@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @ApiBaseUrl
    fun provideApiBaseUrl(): String = BuildConfig.BASE_URL
}
