package fm.mimo.data.di

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import fm.mimo.data.remote.LessonsApi
import fm.mimo.data.repository.LessonsRepositoryImpl
import fm.mimo.data.source.DataSourceFileReader
import fm.mimo.data.source.LessonsDataSource
import fm.mimo.data.source.LessonsMockDataSource
import fm.mimo.data.source.LessonsRemoteDataSource
import fm.mimo.di_android.BuildFlavorMock
import fm.mimo.domain.repository.LessonsRepository
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataModule {
    @Provides
    @ActivityRetainedScoped
    fun provideLessonsDataSource(
        lessonsApi: LessonsApi,
        @BuildFlavorMock mockEnv: Boolean,
        dataSourceFileReader: DataSourceFileReader,
    ): LessonsDataSource {
        return if (mockEnv)
            LessonsMockDataSource(dataSourceFileReader)
        else LessonsRemoteDataSource(lessonsApi)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDataSourceFileReader(
        @ApplicationContext context: Context,
        gson: Gson
    ): DataSourceFileReader = DataSourceFileReader(context = context, gson = gson)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindLessonsRepository(lessonsRepositoryImpl: LessonsRepositoryImpl): LessonsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideLessonsApi(client: Retrofit): LessonsApi = client.create(LessonsApi::class.java)
}
