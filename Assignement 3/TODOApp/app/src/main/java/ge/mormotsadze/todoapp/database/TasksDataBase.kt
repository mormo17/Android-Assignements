package ge.mormotsadze.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ge.mormotsadze.todoapp.database.task.Task
import ge.mormotsadze.todoapp.database.task.TaskDao
import ge.mormotsadze.todoapp.database.subtask.SubTask
import ge.mormotsadze.todoapp.database.subtask.SubTaskDao

@Database(entities = [Task::class, SubTask::class], version = 1)
@TypeConverters(Converters::class)
abstract class TasksDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getSubTaskDao(): SubTaskDao
}