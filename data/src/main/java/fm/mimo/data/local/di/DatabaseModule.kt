package fm.mimo.data.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fm.mimo.data.local.MimoDatabase
import fm.mimo.data.local.dao.LessonCompletionEventDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMimoDatabase(@ApplicationContext context: Context): MimoDatabase {
        return Room.databaseBuilder(
            context,
            MimoDatabase::class.java,
            "mimo-database"
        ).build()
    }

    @Provides
    fun provideLessonCompletionEventDao(database: MimoDatabase): LessonCompletionEventDao {
        return database.lessonCompletionEventDao()
    }
}
