package ge.mormotsadze.todoapp.todolistcells.addlistcell

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ge.mormotsadze.todoapp.R

class AddListCell (context: Context?) : ConstraintLayout(context!!) {

    private var addTaskIcon: ImageView? = null
    private var taskDescription: TextView? = null

    init {
        inflate(context, R.layout.add_list_cell, this)
        addTaskIcon = findViewById(R.id.list_cell_add_icon)
        taskDescription = findViewById(R.id.todo_list_cell_text_field)
    }

    fun setUp(model: AddListCellModel) {
        addTaskIcon?.setImageResource(model.id)
        taskDescription?.text = model.text
    }

    fun setDescription(newText: String) {
        taskDescription?.text = newText
    }
}