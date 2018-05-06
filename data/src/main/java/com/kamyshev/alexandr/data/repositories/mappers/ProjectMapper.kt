package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.*
import io.realm.RealmList

/**
 * Created by alexandr on 20.01.18.
 */
object ProjectMapper : BaseMapper<Project, ProjectDbModel> {

    override fun map(from: Project): ProjectDbModel {
        val tasks = from.tasks
        val tasksdb: RealmList<TaskDbModel> = RealmList()

        tasks.forEach {
            val subTasks = it.subTasks
            val subtasksdb = RealmList<SubTaskDbModel>()

            subTasks.forEach {
                subtasksdb.add(SubTaskDbModel(it.name, it.isComplete))
            }

            tasksdb.add(TaskDbModel(it.name, it.progress, subtasksdb))
        }

        val projectDb = ProjectDbModel(from.name, from.progress, tasksdb, from.bgColor)
        projectDb.key = from.key
        return projectDb
    }

    fun toDb(from: ProjectDbModel) {

    }
}