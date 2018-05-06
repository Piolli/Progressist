package com.kamyshev.alexandr.presentation.ui.taskslist

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.onDeleteProject
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.task_list_item.view.*

class TasksListAdapter(val data: MutableList<Task>, val onDeleteTask: OnDeleteTask) : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    inner class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(task: Task) {
            view.task_name.text = task.name
            view.sub_task_recycler_view.layoutManager = LinearLayoutManager(view.context)
            view.task_parent_view.setOnClickListener {
                if(view.expandable_layout.isExpanded)
                    view.expandable_layout.collapse()
                else
                    view.expandable_layout.expand()
            }
            val mutableSubTasks = task.subTasks.toMutableList()
            mutableSubTasks.add(SubTask("Create design", false))
            mutableSubTasks.add(SubTask("More profit", true))
            mutableSubTasks.add(SubTask("Hello", false))
            view.sub_task_recycler_view.adapter = SubTasksListAdapter(mutableSubTasks)
        }
    }

    interface OnDeleteTask {
        fun onDelete(task: Task)
    }

}