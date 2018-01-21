package com.kamyshev.alexandr.domain.global.projectslist

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.repositories.ProjectsListRepository

/**
 * Created by alexandr on 20.01.18.
 */
class ProjectsListInteractor(val projectsListRepository: ProjectsListRepository) {

    fun getProjects() = projectsListRepository.getProjects()

    fun getProjectByID(id: Int) = projectsListRepository.getProjectByID(id)

    fun saveProject(project: Project) = projectsListRepository.saveProject(project)
}