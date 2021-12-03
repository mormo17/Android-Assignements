package ge.mormotsadze.todoapp.todosmainpage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import ge.mormotsadze.todoapp.database.task.Task
import ge.mormotsadze.todoapp.dataclasses.Tasks
import ge.mormotsadze.todoapp.adapters.RecyclerViewAdapter
import ge.mormotsadze.todoapp.R
import ge.mormotsadze.todoapp.constants.CELL_TYPE
import ge.mormotsadze.todoapp.constants.PARCELABLE_KEY
import ge.mormotsadze.todoapp.constants.TODO_CELL_TYPE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class TodosMainPageFragment : Fragment(), TodosMainPageContract.View {

    private lateinit var searchBox: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNoteButton: ImageView
    private lateinit var presenter: TodosMainPageContract.Presenter
    private lateinit var navController: NavController
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainPageContext: Context
    private var todoListCells : MutableList<Tasks> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainPageContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todos_main_page_fragment, container, false)
        init(view)
        updateTasks("")
        setListeners()
        hideProgressBar()

        return view
    }

    private fun init(view: View) {
        searchBox = view.findViewById(R.id.todos_main_page_search_box)
        progressBar = view.findViewById(R.id.todos_main_page_progress_bar)
        addNoteButton = view.findViewById(R.id.add_todo_note_button)

        activity?.applicationContext?.let {
            presenter = TodosMainPagePresenter(this, it)
        }
        navController = findNavController()

        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.todos_main_page_recycler_view)
        recyclerViewAdapter = RecyclerViewAdapter(navController, this.context)

        val gridLayoutManager = GridLayoutManager(activity, 2)

        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (recyclerViewAdapter.getItemViewType(position)) {
                    CELL_TYPE -> 2
                    TODO_CELL_TYPE -> 1
                    else -> 2
                }
            }
        }

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun updateTasks(searchWord: String) {
        GlobalScope.launch {
            todoListCells = presenter.getSearchedTasks(searchWord)

            (mainPageContext as Activity).runOnUiThread {
                recyclerViewAdapter.setUp(todoListCells)
            }
        }
    }

    private fun setListeners() {
        setSearchBoxListener()
        setAddNoteButtonListener()
    }

    private fun setAddNoteButtonListener(){
        addNoteButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(PARCELABLE_KEY, Tasks(Task(0, "", false, Date()), ArrayList()))
            navController.navigate(R.id.action_todos_main_page_fragment_to_todo_list_fragment, bundle)
        }
    }

    private fun setSearchBoxListener(){
        searchBox.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                updateTasks(searchBox.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun showProgressBar() {
        (mainPageContext as Activity).runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        (mainPageContext as Activity).runOnUiThread {
            progressBar.visibility = View.INVISIBLE
        }
    }
}