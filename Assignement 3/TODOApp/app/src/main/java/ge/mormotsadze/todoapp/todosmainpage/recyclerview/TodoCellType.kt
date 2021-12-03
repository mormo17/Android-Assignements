package ge.mormotsadze.todoapp.todosmainpage.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.mormotsadze.todoapp.R


class TodoCellType(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var cellType: TextView? = null

    init {
        cellType = itemView.findViewById(R.id.pinned_text_view)
    }

    fun setUpView(title : String) {
        cellType?.text = title
    }
}