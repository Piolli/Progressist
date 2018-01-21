package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.SubTaskDbModel

/**
 * Created by alexandr on 19.01.18.
 */
object SubTaskDbModelMapper : BaseMapper<List<SubTaskDbModel>, List<SubTask>> {

    override fun map(from: List<SubTaskDbModel>): List<SubTask> {
        return from.map(SubTaskDbModel::toSubTask)
    }
}