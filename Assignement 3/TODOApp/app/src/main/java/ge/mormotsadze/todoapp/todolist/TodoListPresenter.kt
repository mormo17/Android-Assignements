package ge.mormotsadze.todoapp.todolist

import android.content.Context
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.todosmainpage.TodoListModel


class TodoListPresenter(var view: TodoListInterface.View, var context: Context) : TodoListInterface.Presenter {

    private var model: TodoListInterface.Model = TodoListModel(context)

    override fun saveTask(tasks: Tasks) {
        view.showProgressBar()
        model.saveTask(tasks)
        view.hideProgressBar()
    }

}