package ge.mormotsadze.todoapp.dataclasses

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import ge.mormotsadze.todoapp.database.task.Task
import ge.mormotsadze.todoapp.database.subtask.SubTask
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tasks(
    @Embedded var task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val tasks: MutableList<SubTask>
) : Parcelable