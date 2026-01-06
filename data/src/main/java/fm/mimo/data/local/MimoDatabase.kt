package fm.mimo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fm.mimo.data.local.dao.LessonCompletionEventDao
import fm.mimo.data.local.model.LessonCompletionEvent

@Database(entities = [LessonCompletionEvent::class], version = 1)
abstract class MimoDatabase : RoomDatabase() {

    abstract fun lessonCompletionEventDao(): LessonCompletionEventDao
}
