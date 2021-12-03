package ge.mormotsadze.todoapp.database.task

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import ge.mormotsadze.todoapp.constants.TASK_TABLE_NAME
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = TASK_TABLE_NAME)
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var pinned: Boolean,
    var updateDate: Date
) : Parcelable
