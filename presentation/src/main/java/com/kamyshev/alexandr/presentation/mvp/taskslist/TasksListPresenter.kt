package com.kamyshev.alexandr.presentation.mvp.taskslist

import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.taskslist.TasksListInteractor
import com.kamyshev.alexandr.presentation.mvp.base.BasePresenter
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListAdapter

class TasksListPresenter(override var view: TasksListView,
                         val projectKey: String,
                         val tasksListInteractor: TasksListInteractor,
                         val adapter: TasksListAdapter) : BasePresenter<TasksListView> {



    fun addTask(task: Task) {
        tasksListInteractor.addTaskToProject(task, projectKey)
        adapter.data.add(0, task)
        adapter.notifyItemInserted(0)
        view.scrollRecyclerViewToPosition(0)
    }

    fun deleteTasks(task: Task) {
        tasksListInteractor.deleteTaskFromProject(task, projectKey)
    }

}