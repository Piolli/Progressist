package com.kamyshev.alexandr.domain.global.repositories

import com.kamyshev.alexandr.domain.global.models.Task

interface TasksListRepository {

    fun addTaskToProject(task: Task, projectKey: String)

    fun deleteTaskFromProject(task: Task, projectKey: String)

}