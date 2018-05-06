package com.kamyshev.alexandr.domain.global.projectslist

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.repositories.ProjectsListRepository

/**
 * Created by alexandr on 20.01.18.
 */
class ProjectsListInteractor(val projectsListRepository: ProjectsListRepository): ProjectsListRepository {

    override fun getProjects() = projectsListRepository.getProjects().reversed()

    override fun getProjectByID(key: String) = projectsListRepository.getProjectByID(key)

    override fun addProject(project: Project) = projectsListRepository.addProject(project)

    override fun deleteProjectByID(key: String) = projectsListRepository.deleteProjectByID(key)

}