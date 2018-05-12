package com.kamyshev.alexandr.domain.global.repositories

import com.kamyshev.alexandr.domain.global.models.Project

/**
 * Created by alexandr on 19.01.18.
 */
interface ProjectsListRepository {

    fun getProjects(): List<Project>

    fun getProjectByID(key: String): Project

    fun addProject(project: Project)

    fun deleteProjectByID(key: String)

    fun updateProject(newProject: Project, oldProjectKey: String)

}