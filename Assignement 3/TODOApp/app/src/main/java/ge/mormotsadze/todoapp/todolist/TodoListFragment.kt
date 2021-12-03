package ge.mormotsadze.todoapp.todolist

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ge.mormotsadze.todoapp.todolistcells.checkedlistcell.CheckedListCell
import ge.mormotsadze.todoapp.todolistcells.checkedlistcell.CheckedListCellModel
import ge.mormotsadze.todoapp.todolistcells.addlistcell.AddListCell
import ge.mormotsadze.todoapp.todolistcells.addlistcell.AddListCellModel
import ge.mormotsadze.todoapp.database.subtask.SubTask
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.R
import ge.mormotsadze.todoapp.constants.CHECKED
import ge.mormotsadze.todoapp.constants.ITEM
import ge.mormotsadze.todoapp.constants.ITEMS
import ge.mormotsadze.todoapp.constants.PARCELABLE_KEY
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class TodoListFragment : Fragment(), TodoListInterface.View, CheckedListCell.CheckedListCellClick {

    private lateinit var backIcon: ImageView
    private lateinit var pinIcon: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var tasksLayout: LinearLayout
    private lateinit var presenter: TodoListInterface.Presenter
    private lateinit var tasks: Tasks
    private lateinit var todoListCellTitle: EditText
    private lateinit var addListCell: AddListCell
    private lateinit var checkedAddListCell: AddListCell
    private lateinit var todoListContext: Context

    private var uncheckedIndx: Int = 1
    private var checkedTasksCount: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        todoListContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_list_fragment, container, false)
        tasks = arguments?.get(PARCELABLE_KEY) as Tasks

        init(view)
        setUp(tasks)

        hideProgressBar()

        return view
    }

    private fun init(view: View) {
        activity?.applicationContext?.let {
            presenter = TodoListPresenter(this, it)
        }

        initTodoListCellTitle()
        initTodoListFragmentItems(view)
        initAddListCells()
        setListeners()
    }

    private fun initAddListCells() {
        initAddListCell()
        initCheckedAddListCell()
    }

    private fun initCheckedAddListCell() {
        checkedAddListCell = AddListCell(context)
    }

    private fun initAddListCell() {
        addListCell = AddListCell(context)
    }

    private fun initTodoListFragmentItems(view: View) {
        initProgressBar(view)
        initPinIcon(view)
        initBackIcon(view)
        initTasksLayout(view)
    }

    private fun initTasksLayout(view: View) {
        tasksLayout = view.findViewById(R.id.todos_list)
        tasksLayout.addView(todoListCellTitle)
    }

    private fun initBackIcon(view: View) {
        backIcon = view.findViewById(R.id.todo_list_back_icon)
    }

    private fun initPinIcon(view: View) {
        pinIcon = view.findViewById(R.id.todo_list_pin_icon)
        pinIcon.setImageResource(if (tasks.task.pinned) R.drawable.ic_pinned else R.drawable.ic_pin)
    }

    private fun initProgressBar(view: View) {
        progressBar = view.findViewById(R.id.todo_list_progress_bar)
    }

    private fun initTodoListCellTitle(){
        todoListCellTitle = EditText(context)
        todoListCellTitle.setBackgroundResource(android.R.color.transparent)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(30, 0, 0, 30)
        todoListCellTitle.layoutParams = params
        todoListCellTitle.textSize = 26.0F
        todoListCellTitle.hint = "Type name here"
        todoListCellTitle.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        todoListCellTitle.setText(tasks.task.title)
    }

    private fun setUp(tasksList: Tasks) {
        iterateTasks(tasksList, false)
        addImagedTextView(AddListCellModel(R.drawable.ic_add, "List item"), addListCell)
        addSeparator()
        iterateTasks(tasksList, true)
    }

    private fun iterateTasks(taskList: Tasks, checked: Boolean){
        taskList.tasks.forEach {
            if (it.isChecked == checked) {
                if (!checked) uncheckedIndx++
                addTask(
                    CheckedListCellModel(
                        it.id,
                        checked,
                        it.description
                    )
                )
            } else if(!checked){
                checkedTasksCount++
            }
        }
    }

    private fun addTask(model: CheckedListCellModel, index: Int = -1) {
        val subTask =
            CheckedListCell(
                context
            )

        val layoutParams = getLayoutParams()

        layoutParams.setMargins(50, 0, 0, 0)
        subTask.layoutParams = layoutParams
        subTask.setUp(model, this)

        tasksLayout.addView(subTask, index)
    }

    private fun addImagedTextView(model: AddListCellModel, addListCell: AddListCell) {
        val layoutParams = getLayoutParams()
        layoutParams.setMargins(50, 0, 0, 0)
        addListCell.layoutParams = layoutParams
        addListCell.setUp(model)
        tasksLayout.addView(addListCell)
    }

    private fun getLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun addSeparator() {
        val separator = ImageView(context)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)
        layoutParams.setMargins(0, 20, 0, 20)
        separator.layoutParams = layoutParams
        separator.setBackgroundColor(Color.GRAY)
        tasksLayout.addView(separator)
    }

    private fun setListeners() {
        setPinIconListener()
        setBackIconListener()
        setAddListCellListener()
    }

    private fun setAddListCellListener() {
        addListCell.setOnClickListener {
            addTask(CheckedListCellModel(0, false, ""), uncheckedIndx++)
        }
    }

    private fun setBackIconListener() {
        backIcon.setOnClickListener {
            GlobalScope.launch {
                presenter.saveTask(getTask())
                (todoListContext as Activity).runOnUiThread {
                    findNavController().navigate(R.id.action_page_fragment_to_action_main_page_fragment)
                }
            }
        }
    }

    private fun setPinIconListener() {
        pinIcon.setOnClickListener {
            tasks.task.pinned = !tasks.task.pinned
            pinIcon.setImageResource(if (tasks.task.pinned) R.drawable.ic_pinned else R.drawable.ic_pin)
        }
    }

    private fun getTask() : Tasks {
        tasks.task.title = todoListCellTitle.text.toString()

        tasks.tasks.clear()

        for (i in 0 until tasksLayout.childCount) {
            val task: View = tasksLayout.getChildAt(i)

            if (task is CheckedListCell) {
                tasks.tasks.add(SubTask(task.getTaskId(), tasks.task.id, task.getDescription(), task.isChecked()))
            }
        }

        return tasks
    }

    private fun updateCheckedTasksCount() {
        checkedAddListCell.setDescription("+${checkedTasksCount} " + CHECKED + " ${if (checkedTasksCount == 1) "" + ITEM + "" else ITEMS}")
    }

    override fun onClick(checkedListCell: CheckedListCell, newValue: Boolean) {
        tasksLayout.removeView(checkedListCell)

        if (newValue) {
            checkedTasksCount++
            uncheckedIndx--
            tasksLayout.addView(checkedListCell)
        } else {
            checkedTasksCount--
            tasksLayout.addView(checkedListCell, uncheckedIndx)
            uncheckedIndx++
        }
        checkedListCell.enable(!newValue)
        updateCheckedTasksCount()
    }

    override fun showProgressBar() {
        (todoListContext as Activity).runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        (todoListContext as Activity).runOnUiThread {
            progressBar.visibility = View.INVISIBLE
        }
    }
}