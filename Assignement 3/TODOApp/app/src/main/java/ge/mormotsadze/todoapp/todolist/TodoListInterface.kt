package ge.mormotsadze.todoapp.todolist

import ge.mormotsadze.todoapp.dataclasses.Tasks

interface TodoListInterface {
    interface Presenter {
        fun saveTask(tasks: Tasks)
    }

    interface Model {
        fun saveTask(tasks: Tasks)
    }

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }
}