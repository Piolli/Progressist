package com.kamyshev.alexandr.presentation.mvp.projectslist

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.mvp.base.BasePresenter

/**
 * Created by alexandr on 19.01.18.
 */
class ProjectsListPresenter(override var view: ProjectsListView,
                            val projectsListInteractor: ProjectsListInteractor)
    : BasePresenter<ProjectsListView> {

    fun loadProjects() {
        val projects = projectsListInteractor.getProjects()

    }

    fun addProject(project: Project) {
        projectsListInteractor.saveProject(project)
    }

    fun deleteProject() {

    }
}