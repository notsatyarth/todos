package com.app.todos.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.app.todos.R
import com.app.todos.di.Repository
import com.app.todos.ui.adapters.ToDoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotterknife.bindView
import javax.inject.Inject

class ToDoListActivity : AppCompatActivity() {


    @Inject
    lateinit var repository: Repository

    private val todoList by bindView<RecyclerView>(R.id.todolist)

    private val inputField by bindView<EditText>(R.id.input)

    lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputField.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    inputField.text.clear()
                    addMessage(ToDoItem(inputField.text.toString()))
                    true
                }
                else -> false
            }
        }

        setUp()
    }

    private fun setUp() {
        adapter = ToDoAdapter()
        todoList.adapter = adapter
        todoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        GlobalScope.launch {
            repository.itemList().consumeEach {
                setData(it)
            }
        }
    }

    private suspend fun setData(data: List<ToDoItem>) = withContext(Dispatchers.Main) {
        adapter.setData(data)
    }

    private fun addMessage(toDoItem: ToDoItem) {
        repository.addItem(toDoItem)
    }
}



