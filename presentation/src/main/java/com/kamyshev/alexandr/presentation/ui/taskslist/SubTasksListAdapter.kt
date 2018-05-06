package com.kamyshev.alexandr.presentation.ui.taskslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.presentation.R
import kotlinx.android.synthetic.main.sub_task_item.view.*

class SubTasksListAdapter(val subTasks: MutableList<SubTask>) : RecyclerView.Adapter<SubTasksListAdapter.SubTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SubTaskViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.sub_task_item, parent, false)
        return SubTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subTasks.size
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder?, position: Int) {
        holder?.bind(subTasks[position])
    }

    inner class SubTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(subTask: SubTask) {
            itemView.sub_task_name.text = subTask.name
            itemView.sub_task_check_box.isChecked = subTask.isComplete
        }
    }
}