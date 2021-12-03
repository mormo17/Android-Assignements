package ge.mormotsadze.todoapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ge.mormotsadze.todoapp.R
import ge.mormotsadze.todoapp.constants.*
import ge.mormotsadze.todoapp.database.task.Task
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.todosmainpage.recyclerview.TodoCellType
import ge.mormotsadze.todoapp.todosmainpage.recyclerview.TodoListCell
import java.util.*
import kotlin.collections.ArrayList


class RecyclerViewAdapter(private val navController: NavController, val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var todoCells: MutableList<Tasks> = ArrayList()

    private var clickListener = View.OnClickListener {
        val bundle = Bundle()
        bundle.putParcelable(PARCELABLE_KEY, todoCells[it.tag as Int])
        navController.navigate(R.id.action_todos_main_page_fragment_to_todo_list_fragment, bundle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cellView: View
        return if (viewType == TODO_CELL_TYPE) {
            cellView = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_cell, parent, false)
            cellView.setOnClickListener(clickListener)
            TodoListCell(cellView, context)
        } else {
            cellView = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_cell_type, parent, false)
            TodoCellType(cellView)
        }
    }

    override fun getItemCount(): Int {
        return todoCells.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TodoListCell) {
            holder.setUp(todoCells[position])
            holder.itemView.tag = holder.adapterPosition
        } else if (holder is TodoCellType) {
            holder.setUpView(todoCells[position].task.title)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (todoCells[position].task.id == TODO_CELL_ID) {
            return CELL_TYPE
        }
        return TODO_CELL_TYPE
    }

    fun setUp(tasks: MutableList<Tasks>) {
        todoCells.clear()

        val isPinned = iterateTasks(tasks,
            false,
            wasUnpinned = true,
            isTaskPinned = true,
            pinned = false,
            cellType = PINNED
        )

        iterateTasks(tasks,
            isPinned,
            wasUnpinned = false,
            isTaskPinned = false,
            pinned = false,
            cellType = OTHERS
        )


        notifyDataSetChanged()
    }

    private fun iterateTasks(tasks: MutableList<Tasks>,
                             wasPinned: Boolean,
                             wasUnpinned: Boolean,
                             isTaskPinned: Boolean,
                             pinned: Boolean,
                             cellType: String): Boolean{
        var isPinned = wasPinned
        var unPinnedTask = wasUnpinned
        tasks.forEach {
            if (it.task.pinned == isTaskPinned) {
                if (!isPinned) {
                    todoCells.add(Tasks(Task( TODO_CELL_ID, cellType, pinned, Date()), ArrayList()))
                } else if(isPinned && !unPinnedTask){
                    todoCells.add(Tasks(Task(TODO_CELL_ID, OTHERS, pinned, Date()), ArrayList()))
                }

                todoCells.add(it)
                isPinned = true
                unPinnedTask = true
            }
        }
        return isPinned
    }
}