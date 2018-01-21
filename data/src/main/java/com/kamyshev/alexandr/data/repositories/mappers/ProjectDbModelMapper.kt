package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel

/**
 * Created by alexandr on 19.01.18.
 */
object ProjectDbModelMapper : BaseMapper<List<ProjectDbModel>, List<Project>> {

    override fun map(from: List<ProjectDbModel>): List<Project> {
        return from.map(ProjectDbModel::toProject)
    }
}