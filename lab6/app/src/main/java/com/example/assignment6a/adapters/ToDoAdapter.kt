package com.example.assignment6a.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment6a.R
import com.example.assignment6a.REPOSITORY
import com.example.assignment6a.models.TaskModel
import com.example.assignment6a.screens.todolist.ToDoListFragment
import kotlinx.android.synthetic.main.task_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToDoAdapter: RecyclerView.Adapter<ToDoAdapter.ToDoListViewHolder>() {

    var toDoList = emptyList<TaskModel>()

    class ToDoListViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return ToDoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.itemView.checkbox.text = toDoList[position].title
        holder.itemView.checkbox.isChecked = toDoList[position].status
        if (toDoList[position].status) holder.itemView.checkbox.paintFlags = holder.itemView.checkbox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else holder.itemView.checkbox.paintFlags = holder.itemView.checkbox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TaskModel>) {
        toDoList = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: ToDoListViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            ToDoListFragment.clickTask(toDoList[holder.adapterPosition])
        }
        holder.itemView.del.setOnClickListener {
            ToDoListFragment.delTask(toDoList[holder.adapterPosition])
        }
        holder.itemView.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                ToDoListFragment.updateTask(toDoList[holder.adapterPosition])
            } else {
                ToDoListFragment.updateTask(toDoList[holder.adapterPosition])
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ToDoListViewHolder) {
        holder.itemView.setOnClickListener(null)
        holder.itemView.del.setOnClickListener(null)
        holder.itemView.checkbox.setOnCheckedChangeListener(null)
    }

}