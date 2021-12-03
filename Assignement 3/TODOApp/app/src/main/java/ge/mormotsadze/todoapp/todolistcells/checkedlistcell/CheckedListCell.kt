package ge.mormotsadze.todoapp.todolistcells.checkedlistcell

import android.content.Context
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import ge.mormotsadze.todoapp.R


class CheckedListCell(context: Context?) : ConstraintLayout(context!!) {

    private var taskId: Int = 0
    private var checkBox: CheckBox? = null
    private var taskDescription: EditText? = null
    private var deleteIcon: ImageView


    init {
        inflate(context, R.layout.checked_list_cell, this)
        checkBox = findViewById(R.id.todo_list_checkbox)
        taskDescription = findViewById(R.id.todo_list_checked_item_text_field)
        deleteIcon = findViewById(R.id.todo_list_checked_item_delete_icon)
    }

    fun setUp(model: CheckedListCellModel, checkedListCellClick: CheckedListCellClick? = null) {
        taskId = model.subNoteId
        checkBox?.isChecked = model.checked
        taskDescription?.setText(model.description)

        setListeners(checkedListCellClick)
        enable(!model.checked)
        requestLayout()
    }

    private fun setListeners(checkedListCellClick: CheckedListCellClick? = null){
        setCheckBoxListener(checkedListCellClick)
        setTaskDescriptionListener()
        setDeleteIconListener()
    }

    private fun setCheckBoxListener(checkedListCellClick: CheckedListCellClick? = null){
        checkBox?.setOnClickListener {
            checkedListCellClick?.onClick(this, checkBox?.isChecked!!)
        }
    }

    private fun setTaskDescriptionListener(){
        taskDescription?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                deleteIcon.visibility = VISIBLE
            } else {
                deleteIcon.visibility = GONE
            }
        }
    }

    private fun setDeleteIconListener(){
        deleteIcon.setOnClickListener(){
            this.visibility = GONE
            taskId = -1
        }
    }

    fun getTaskId() : Int {
        return taskId
    }

    fun getDescription() : String {
        return taskDescription?.text.toString()
    }

    fun isChecked() : Boolean {
        return checkBox!!.isChecked
    }

    fun enable(value: Boolean) {
        taskDescription?.isEnabled = value
    }

    interface CheckedListCellClick {
        fun onClick(checkedListCell: CheckedListCell, newValue: Boolean)
    }
}
