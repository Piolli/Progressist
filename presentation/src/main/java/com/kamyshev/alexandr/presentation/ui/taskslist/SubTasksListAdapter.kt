package com.kamyshev.alexandr.presentation.ui.taskslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamyshev.alexandr.data.repositories.TasksListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.taskslist.TasksListInteractor
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.onDeleteProject
import com.kamyshev.alexandr.presentation.utils.DialogFactory
import kotlinx.android.synthetic.main.sub_task_item.view.*

class SubTasksListAdapter(val task: Task,
                          val onDeleteSubTask: OnDeleteSubTask,
                          val onChangeCheckedSubTask: OnChangeCheckedSubTask)
    : RecyclerView.Adapter<SubTasksListAdapter.SubTaskViewHolder>() {

    val subTasks = task.subTasks.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_task_item, parent, false)
        return SubTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subTasks.size
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        holder.bind(subTasks[position], holder.adapterPosition)
    }

    inner class SubTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(subTask: SubTask, position: Int) {
            itemView.sub_task_name.text = subTask.name
            itemView.sub_task_check_box.isChecked = subTask.isComplete
            itemView.sub_task_check_box.setOnCheckedChangeListener { buttonView, isChecked ->
                onChangeCheckedSubTask.onChangeCheckedSubTask(task, subTask, isChecked)
            }
            itemView.setOnLongClickListener {
                DialogFactory.dialog2(itemView.context, "Удалить подзадвчу?", "Да", "Закрыть") {
                    when(it) {
                        DialogFactory.ANSWER.POSITIVE -> {
                            onDeleteSubTask.onDeleteSubTask(task, subTask)
                            subTasks.remove(subTask)
                            notifyItemRemoved(position)
                        }
                    }
                }
                true
            }
        }
    }

    interface OnDeleteSubTask {
        fun onDeleteSubTask(task: Task, subTask: SubTask)
    }

    interface OnChangeCheckedSubTask {
        fun onChangeCheckedSubTask(task: Task, subTask: SubTask, isChecked: Boolean)
    }
}