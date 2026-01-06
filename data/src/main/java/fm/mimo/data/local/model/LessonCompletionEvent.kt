package fm.mimo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_completion_events")
data class LessonCompletionEvent(
    @PrimaryKey val lessonId: Int,
    val startedAt: Long,
    val completedAt: Long,
)
