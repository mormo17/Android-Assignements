package ge.mormotsadze.todoapp.todosmainpage

import android.content.Context
import androidx.room.Room
import androidx.room.Transaction
import ge.mormotsadze.todoapp.constants.TASKS_DATA_BASE
import ge.mormotsadze.todoapp.database.TasksDataBase
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.todolist.TodoListInterface
import java.util.*

class TodoListModel(var context: Context) : TodoListInterface.Model {

    private var database: TasksDataBase = Room.databaseBuilder(context, TasksDataBase::class.java, TASKS_DATA_BASE)
        .fallbackToDestructiveMigration()
        .build()


    @Transaction
    override fun saveTask(tasks: Tasks) {
        if (tasks.task.title.isEmpty()) {
            database.getTaskDao().deleteTask(task = tasks.task)
            return
        }

        tasks.task.updateDate = Date()
        val newNoteId = database.getTaskDao().insertTask(tasks.task)
        tasks.tasks.forEach {
            it.taskId = newNoteId.toInt()
            if (it.description.isEmpty() || it.id < 0) { // sub task id will be less then zero when X is pressed
                database.getSubTaskDao().deleteTask(it)
            } else {
                database.getSubTaskDao().insertTask(it)
            }
        }
    }


}