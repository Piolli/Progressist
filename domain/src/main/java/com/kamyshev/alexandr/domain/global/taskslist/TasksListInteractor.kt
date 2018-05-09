package com.kamyshev.alexandr.domain.global.taskslist

import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.repositories.TasksListRepository

class TasksListInteractor(val repositoryImpl: TasksListRepository): TasksListRepository {

    override fun addTaskToProject(task: Task, projectKey: String) {
        repositoryImpl.addTaskToProject(task, projectKey)
    }

    override fun deleteTaskFromProject(task: Task, projectKey: String) {
        repositoryImpl.deleteTaskFromProject(task, projectKey)
    }

    override fun addSubTaskToTask(task: Task, subTask: SubTask, projectKey: String) {
        repositoryImpl.addSubTaskToTask(task, subTask, projectKey)
    }

    override fun deleteSubTaskFromTask(task: Task, subTask: SubTask, projectKey: String) {
        repositoryImpl.deleteSubTaskFromTask(task, subTask, projectKey)
    }

    override fun setCheckedSubTask(task: Task, subTask: SubTask, projectKey: String, isChecked: Boolean) {
        repositoryImpl.setCheckedSubTask(task, subTask, projectKey, isChecked)
    }
}