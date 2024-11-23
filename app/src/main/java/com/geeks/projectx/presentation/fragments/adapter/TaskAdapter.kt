package com.geeks.projectx.presentation.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.geeks.projectx.databinding.ItemTaskBinding
import com.geeks.projectx.presentation.model.TaskUI

class TaskAdapter(
    private var taskList: List<TaskUI>,
    private val onItemClick: (id: Int) -> Unit,
    private val onTaskDelete: (TaskUI) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskUI) {
            binding.tvTask.text = task.taskName
            binding.tvDate.text = task.taskDate
            binding.root.setOnClickListener { onItemClick(task.id) }
        }
    }

    private val itemTouch = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val taskDeleted = taskList[position]
            onTaskDelete(taskDeleted)
        }
    }

    fun attachSwipeToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    fun updateTasks(newTasks: List<TaskUI>) {
        taskList = newTasks
        notifyDataSetChanged()
    }
}