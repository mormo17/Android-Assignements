package ge.mormotsadze.todoapp.todosmainpage.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.mormotsadze.todoapp.todolistcells.checkedlistcell.CheckedListCellModel
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.R
import ge.mormotsadze.todoapp.constants.*

class TodoListCell(itemView: View, var context: Context?) : RecyclerView.ViewHolder(itemView) {

    private var todoListCellTitle: TextView? = null
    private var layout: LinearLayout? = null
    private var checkedTasksCount: TextView? = null
    private var uncheckedTasksCount: TextView? = null


    init {
        todoListCellTitle = itemView.findViewById(R.id.todo_list_cell_title)
        layout = itemView.findViewById(R.id.todo_list_cell_layout)
        checkedTasksCount = itemView.findViewById(R.id.todo_list_checked_cell_count)
        uncheckedTasksCount = itemView.findViewById(R.id.todo_list_unchecked_cell_count)

    }

    fun setUp(model : Tasks) {
        todoListCellTitle?.text = model.task.title

        var tasksCount = 0

        var checkedTasksCounter: Int = getTasksCount(model).first
        var uncheckedTasksCounter: Int = getTasksCount(model).second

        layout?.removeAllViews()

        val unchecked = iterateTasks(model,
            checked = false,
            initialTasksCount = tasksCount,
            initialTaskCounter = uncheckedTasksCounter
        )
        tasksCount = unchecked.first
        uncheckedTasksCounter = unchecked.second
        val checked = iterateTasks(model,
            checked = true,
            initialTasksCount = tasksCount,
            initialTaskCounter = checkedTasksCounter
        )

        checkedTasksCounter = checked.second

        layout?.addView(TextView(context))

        updateCheckedTasksCount(checkedTasksCounter)
        updateUncheckedTasksCount(uncheckedTasksCounter)
    }

    private fun iterateTasks(model: Tasks, checked: Boolean, initialTasksCount: Int, initialTaskCounter: Int): Pair<Int, Int>{
        var tasksCount = initialTasksCount
        var tasksCounter = initialTaskCounter

        model.tasks.forEach {
            if (it.isChecked == checked) {
                if (tasksCount < MAX_TASK_COUNT) {
                    addSubNoteItem(
                        CheckedListCellModel(
                            it.id,
                            checked,
                            it.description
                        )
                    )
                    tasksCount++
                    tasksCounter--
                }
            }
        }

        return Pair(tasksCount, tasksCounter)
    }


    private fun getTasksCount(model: Tasks): Pair<Int, Int>{
        var checkedTasksCounter = 0
        var uncheckedTasksCounter = 0
        model.tasks.forEach {
            if (it.isChecked) checkedTasksCounter++
            else uncheckedTasksCounter++
        }

        return Pair(checkedTasksCounter, uncheckedTasksCounter)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUncheckedTasksCount(uncheckedTasksCounter: Int){
        if (uncheckedTasksCounter > 0) {
            val items = if (uncheckedTasksCounter == 1) ITEM else ITEMS
            uncheckedTasksCount?.text = "+${uncheckedTasksCounter}" + UNCHECKED + items
        } else {
            uncheckedTasksCount?.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCheckedTasksCount(checkedTasksCounter: Int){
        if (checkedTasksCounter > 0) {
            val items = if (checkedTasksCounter == 1) ITEM else ITEMS
            this.checkedTasksCount?.text = "+${checkedTasksCounter}" + CHECKED + items
        } else {
            this.checkedTasksCount?.visibility = View.GONE
        }
    }

    private fun addSubNoteItem(model: CheckedListCellModel) {
        val subNoteItem = CheckBox(context)
        subNoteItem.isChecked = model.checked
        subNoteItem.text = model.description
        subNoteItem.isClickable = false
        layout?.addView(subNoteItem)
    }
}