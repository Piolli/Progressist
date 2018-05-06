package com.kamyshev.alexandr.data.repositories

import android.util.Log
import com.kamyshev.alexandr.data.repositories.mappers.ProjectMapper
import com.kamyshev.alexandr.data.repositories.mappers.SubTaskDbModelMapper
import com.kamyshev.alexandr.data.repositories.mappers.TaskDbModelMapper
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel
import com.kamyshev.alexandr.domain.global.models.SubTaskDbModel
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.models.TaskDbModel
import com.kamyshev.alexandr.domain.global.repositories.TasksListRepository
import io.realm.Realm
import io.realm.RealmList

class TasksListRepositoryImpl : TasksListRepository {

    override fun addTaskToProject(task: Task, projectKey: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                val project = it.where(ProjectDbModel::class.java)
                        .equalTo("key", projectKey)
                        .findFirst()
                val subTaskDBRealmList = RealmList<SubTaskDbModel>()
                subTaskDBRealmList.addAll(task.subTasks.map { SubTaskDbModel(it.name, it.isComplete) })

                val taskDb = TaskDbModel(task.name, task.progress, subTaskDBRealmList)
                project?.tasks?.add(taskDb)
            }
        }
    }

    override fun deleteTaskFromProject(task: Task, projectKey: String) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                val project = it.where(ProjectDbModel::class.java)
                        .equalTo("key", projectKey)
                        .findFirst()
                project?.tasks?.find { it.name == task.name }?.deleteFromRealm()
            }
        }
    }

}