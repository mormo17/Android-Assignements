package ge.mormotsadze.todoapp.todosmainpage

import android.content.Context
import androidx.room.Room
import ge.mormotsadze.todoapp.constants.TASKS_DATA_BASE
import ge.mormotsadze.todoapp.database.TasksDataBase
import ge.mormotsadze.todoapp.dataclasses.Tasks

class TodosMainPageModel(var context: Context) :
    TodosMainPageContract.Model {

    private var database: TasksDataBase = Room.databaseBuilder(context, TasksDataBase::class.java, TASKS_DATA_BASE)
        .fallbackToDestructiveMigration()
        .build()

    override fun getSearchedTasks(searchWord: String): MutableList<Tasks> {
        return database.getTaskDao().getSearchedTasks("%${searchWord}%")
    }

}