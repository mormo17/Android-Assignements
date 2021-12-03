package ge.mormotsadze.todoapp.database.subtask

import androidx.room.*
import ge.mormotsadze.todoapp.constants.SUB_TASK_TABLE_NAME
import ge.mormotsadze.todoapp.database.subtask.SubTask

@Dao
interface SubTaskDao {

    @Query("select * from $SUB_TASK_TABLE_NAME where id = :subTaskId")
    fun getSubTask(subTaskId: Int): SubTask

    @Query("select * from $SUB_TASK_TABLE_NAME where taskId = :taskId")
    fun getSubTasks(taskId: Int) : MutableList<SubTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: SubTask)

    @Delete
    fun deleteTask(task: SubTask)
}