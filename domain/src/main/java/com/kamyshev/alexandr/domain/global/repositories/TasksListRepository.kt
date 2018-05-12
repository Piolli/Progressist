package com.kamyshev.alexandr.domain.global.repositories

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task

interface TasksListRepository {

    fun addTaskToProject(task: Task, projectKey: String)

    fun deleteTaskFromProject(task: Task, projectKey: String)

    fun addSubTaskToTask(task: Task, subTask: SubTask, projectKey: String)

    fun deleteSubTaskFromTask(task: Task, subTask: SubTask, projectKey: String)

    fun setCheckedSubTask(task: Task, subTask: SubTask, projectKey: String, isChecked: Boolean)

    fun updateTask(newTask: Task, oldTask: Task, projectKey: String)

}