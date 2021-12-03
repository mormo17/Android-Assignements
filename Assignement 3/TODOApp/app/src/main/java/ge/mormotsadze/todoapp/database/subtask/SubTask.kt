package ge.mormotsadze.todoapp.database.subtask

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ge.mormotsadze.todoapp.constants.SUB_TASK_TABLE_NAME
import ge.mormotsadze.todoapp.database.task.Task
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = SUB_TASK_TABLE_NAME,
    foreignKeys = [
        ForeignKey(entity = Task::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = CASCADE
        )
    ]
)
data class SubTask (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var taskId: Int,
    var description: String,
    var isChecked: Boolean
) : Parcelable
