package com.app.todos.ui.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.todos.R
import com.app.todos.ui.ToDoItem
import kotterknife.bindView

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.VH>() {

    private val data = mutableListOf<ToDoItem>()


    fun setData(todos: List<ToDoItem>) {
        val diff = DiffUtil.calculateDiff(Differ(data, todos))
        data.clear()
        data.addAll(todos)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return VH(LayoutInflater.from(p0.context).inflate(R.layout.item_todo_layout, p0, false))
    }

    override fun getItemCount(): Int =
            data.size


    override fun onBindViewHolder(p0: VH, p1: Int) {
        p0.bind(data[p1])
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view by bindView<TextView>(R.id.todo)

        fun bind(data: ToDoItem) {
            view.text = data.text
        }
    }


}


class Differ(private val old: List<ToDoItem>,
             private val new: List<ToDoItem>) :
        DiffUtil.Callback() {
    override fun areItemsTheSame(oldPos: Int, newPos: Int) = old[oldPos] == new[newPos]

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areContentsTheSame(oldPos: Int, newPos: Int) = old[oldPos] == new[newPos]
}

