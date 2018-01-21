package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.*
import io.realm.RealmList

/**
 * Created by alexandr on 20.01.18.
 */
object ProjectMapper : BaseMapper<Project, ProjectDbModel> {

    override fun map(from: Project): ProjectDbModel {
        val subTasks: RealmList<SubTask> = RealmList()
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


        return ProjectDbModel(from.name, from.progress, tasksdb)
    }

    fun toDb(from: ProjectDbModel) {

    }
}