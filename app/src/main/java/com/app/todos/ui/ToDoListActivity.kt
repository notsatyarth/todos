package com.app.todos.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.TextView
import com.app.todos.R
import com.app.todos.di.Repository
import com.app.todos.ui.adapters.ToDoAdapter
import dagger.android.DaggerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotterknife.bindView
import javax.inject.Inject

class ToDoListActivity : DaggerActivity(), CoroutineScope by MainScope() {


    @Inject
    lateinit var repository: Repository

    private val todoList by bindView<RecyclerView>(R.id.todolist)

    private val inputField by bindView<EditText>(R.id.input)

    private val inputButton by bindView<TextView>(R.id.add_button)

    lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputButton.setOnClickListener {
            addMessage(ToDoItem(inputField.text.toString()))
            inputField.text.clear()
        }
        setUp()
    }

    private fun setUp() {
        adapter = ToDoAdapter()
        todoList.adapter = adapter
        todoList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        launch {
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

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}



