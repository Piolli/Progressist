package com.kamyshev.alexandr.data.repositories.mappers

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel
import com.kamyshev.alexandr.domain.global.models.SubTaskDbModel
import com.kamyshev.alexandr.domain.global.models.TaskDbModel

/**
 * Created by alexandr on 20.01.18.
 */
object ProjectListMapper : BaseMapper<List<Project>, List<ProjectDbModel>> {

    override fun map(from: List<Project>): List<ProjectDbModel> {
        return arrayListOf()
    }
}