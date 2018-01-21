package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.models.TaskDbModel

/**
 * Created by alexandr on 19.01.18.
 */
object TaskDbModelMapper : BaseMapper<List<TaskDbModel>, List<Task>> {

    override fun map(from: List<TaskDbModel>): List<Task> {
        return from.map(TaskDbModel::toTask)
    }
}