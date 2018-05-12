package com.kamyshev.alexandr.presentation.ui.taskslist

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.utils.DialogFactory
import com.muddzdev.styleabletoastlibrary.StyleableToast
import kotlinx.android.synthetic.main.dialog_create_sub_task.view.*
import kotlinx.android.synthetic.main.dialog_create_task.view.*
import kotlinx.android.synthetic.main.task_list_item.view.*

class TasksListAdapter(val data: MutableList<Task>,
                       val onDeleteTask: OnDeleteTask,
                       val onAddSubTask: OnAddSubTask,
                       val onDeleteSubTask: SubTasksListAdapter.OnDeleteSubTask,
                       val onChangeCheckedSubTask: SubTasksListAdapter.OnChangeCheckedSubTask,
                       val onChangeTask: OnChangeTask) :
        RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position], position)
    }


    inner class TaskViewHolder(val view: View) :
            RecyclerView.ViewHolder(view),
            SubTasksListAdapter.OnDeleteSubTask,
            SubTasksListAdapter.OnChangeCheckedSubTask {

        lateinit var adapter: SubTasksListAdapter


        fun bind(task: Task, position: Int) {
            view.task_name.text = task.name

            view.taskProgressView.maxValue = task.subTasks.size
            view.taskProgressView.currentValue = task.progressCount

            view.task_parent_view.setOnLongClickListener {
                DialogFactory.dialogChoose(view.context, "Действие с задачей", arrayOf("Изменить задачу", "Удалить задачу")) {
                    when(it) {
                        //Change task
                        0 -> {
                            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_create_task, null, false)
                            dialogView.taskNameEditText.setText(task.name)
                            val dialog = AlertDialog.Builder(view.context)
                                    .setView(dialogView)
                                    .setTitle("Изменение задачи")
                                    .setPositiveButton("Сохранить") { dialog, which ->
                                        val taskName = dialogView.taskNameEditText.text.toString()
                                        if (!taskName.isEmpty()) {
                                            val newTask = Task(taskName, 0, arrayListOf())
                                            onChangeTask.onChangeTask(newTask, task)
                                            dialog.dismiss()
                                        } else {
                                            StyleableToast.Builder(view.context)
                                                    .text("Неправильно введено название задачи")
                                                    .textColor(Color.WHITE)
                                                    .backgroundColor(ContextCompat.getColor(view.context, R.color.toast_error_color))
                                                    .cornerRadius(3)
                                                    .show()
                                        }
                                    }
                                    .setNegativeButton("Отмена") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .create()

                            dialog.show()
                        }
                        //Delete task
                        1 -> {
                            onDeleteTask.onDelete(task, adapterPosition)
                        }
                    }
                }
                true
            }


            refreshTaskLayoutData(task)
            view.sub_task_recycler_view.layoutManager = LinearLayoutManager(view.context)
            view.task_parent_view.setOnClickListener {
                if(view.expandable_layout.isExpanded)
                    view.expandable_layout.collapse()
                else
                    view.expandable_layout.expand()
            }

            adapter = SubTasksListAdapter(task, this, this)
            view.sub_task_recycler_view.adapter = adapter
            view.add_sub_task_button.setOnClickListener {
                onClick(task, adapter, position)
            }
            if(task.subTasks.isEmpty())
                view.sub_task_recycler_view.visibility = View.GONE
        }

        private fun refreshTaskLayoutData(task: Task) {
            // Update progress view
            view.taskProgressView.maxValue = task.subTasks.size
            view.taskProgressView.currentValue = task.progressCount

            // Update sub title with count completed tasks
            if (task.subTasks.isEmpty()) {
                view.taskCountCompletedTasks.text = "Добавьте подзадачи, нажав на карточку"
            } else {
                view.taskCountCompletedTasks.text = "Выполнено ${task.progressCount} задач(и) из ${task.subTasks.size}"
            }
        }

        override fun onDeleteSubTask(task: Task, subTask: SubTask) {
            val removeSubTask = task.subTasks.toMutableList()
            removeSubTask.remove(subTask)
            task.subTasks = removeSubTask

            if(task.subTasks.isEmpty())
                view.sub_task_recycler_view.visibility = View.GONE

            refreshTaskLayoutData(task)
            onDeleteSubTask.onDeleteSubTask(task, subTask)
        }

        override fun onChangeCheckedSubTask(task: Task, subTask: SubTask, isChecked: Boolean) {
            for(index in task.subTasks.indices) {
                if(task.subTasks[index].name == subTask.name) {
                    task.subTasks[index].isComplete = isChecked
                }
            }
            refreshTaskLayoutData(task)
            onChangeCheckedSubTask.onChangeCheckedSubTask(task, subTask, isChecked)
        }


        fun onClick(task: Task, adapter: SubTasksListAdapter, position: Int) {
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_create_sub_task, null, false)

            val dialog = AlertDialog.Builder(view.context)
                    .setView(dialogView)
                    .setTitle("Добавление подзадачи в задачу")
                    .setCancelable(false)
                    .setPositiveButton("Добавить") { dialog, which ->
                        val subTaskName = dialogView.subTaskNameEditText.text.toString()
                        if (!subTaskName.isEmpty()) {
                            val subTask = SubTask(subTaskName, false)
                            onAddSubTask.onClick(task, subTask)
                            adapter.subTasks.add(subTask)
                            view.sub_task_recycler_view.visibility = View.VISIBLE
                            adapter.notifyItemInserted(adapter.subTasks.size-1)
                            //Refresh task layout from sub task callback
                            task.subTasks = adapter.subTasks
                            refreshTaskLayoutData(task)
                            dialog.dismiss()
                        } else {
                            StyleableToast.Builder(view.context)
                                    .text("Неправильно введено название подзадачи")
                                    .textColor(Color.WHITE)
                                    .backgroundColor(ContextCompat.getColor(view.context, R.color.toast_error_color))
                                    .cornerRadius(3)
                                    .show()
                        }
                    }
                    .setNegativeButton("Закрыть") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()

            dialog.show()
        }

    }

    interface OnDeleteTask {
        fun onDelete(task: Task, position: Int)
    }

    interface OnAddSubTask {
        fun onClick(task: Task, subTask: SubTask)
    }

    interface OnChangeTask {
        fun onChangeTask(newTask: Task, oldTask: Task)
    }

}