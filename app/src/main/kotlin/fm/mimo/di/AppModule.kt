package fm.mimo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fm.mimo.BuildConfig
import fm.mimo.DispatcherProvider
import fm.mimo.DispatcherProviderImpl
import fm.mimo.di_android.ApiBaseUrl
import fm.mimo.di_android.BuildConfigDebug
import fm.mimo.di_android.BuildFlavorMock

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @ApiBaseUrl
    fun provideApiBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @BuildConfigDebug
    fun provideBuildConfigDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @BuildFlavorMock
    fun provideBuildFlavorMock(): Boolean = BuildConfig.MOCK_ENV

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}
