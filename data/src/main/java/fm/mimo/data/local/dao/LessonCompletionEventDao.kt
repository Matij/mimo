package fm.mimo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import fm.mimo.data.local.model.LessonCompletionEvent

@Dao
interface LessonCompletionEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: LessonCompletionEvent)
}
