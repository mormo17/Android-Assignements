package ge.mormotsadze.todoapp.todosmainpage

import android.content.Context
import ge.mormotsadze.todoapp.dataclasses.Tasks

class TodosMainPagePresenter(var view: TodosMainPageContract.View, var context: Context) : TodosMainPageContract.Presenter {

    private var model: TodosMainPageContract.Model = TodosMainPageModel(context)

    override fun getSearchedTasks(searchWord: String): MutableList<Tasks> {
        view.showProgressBar()
        val notes =  model.getSearchedTasks(searchWord)
        view.hideProgressBar()
        return notes
    }
}