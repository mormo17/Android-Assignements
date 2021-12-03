package ge.mormotsadze.todoapp.todosmainpage

import ge.mormotsadze.todoapp.dataclasses.Tasks

interface TodosMainPageContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun getSearchedTasks(searchWord: String) : MutableList<Tasks>
    }

    interface Model {
        fun getSearchedTasks(searchWord: String) : MutableList<Tasks>
    }
}