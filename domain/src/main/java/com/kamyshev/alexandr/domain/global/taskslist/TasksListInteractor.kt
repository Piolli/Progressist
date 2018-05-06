package com.kamyshev.alexandr.domain.global.taskslist

import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.repositories.TasksListRepository

class TasksListInteractor(val repositoryImpl: TasksListRepository): TasksListRepository {

    override fun addTaskToProject(task: Task, projectKey: String) {
        repositoryImpl.addTaskToProject(task, projectKey)
    }

    override fun deleteTaskFromProject(task: Task, projectKey: String) {
        repositoryImpl.deleteTaskFromProject(task, projectKey)
    }

}