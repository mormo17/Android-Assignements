package ge.mormotsadze.todoapp.database.task

import androidx.room.*
import ge.mormotsadze.todoapp.constants.SUB_TASK_TABLE_NAME
import ge.mormotsadze.todoapp.constants.TASK_TABLE_NAME
import ge.mormotsadze.todoapp.dataclasses.Tasks

@Dao
interface TaskDao {

    @Query("select * from $TASK_TABLE_NAME")
    fun getAllTasks(): MutableList<Task>

    @Query("select * from $TASK_TABLE_NAME where id = :taskId")
    fun getTask(taskId: Int): Task

    @Transaction
    @Query(
        """
            select * 
            from $TASK_TABLE_NAME
            where id in (select distinct n.id 
                        from $TASK_TABLE_NAME n 
                        left join $SUB_TASK_TABLE_NAME s 
                        on n.id = s.taskId 
                        Where n.title like :searchWord 
                        or s.description like :searchWord)
            """
    )
    fun getSearchedTasks(searchWord: String) : MutableList<Tasks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task) : Long

    @Delete
    fun deleteTask(task: Task)
}