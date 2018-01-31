package com.kamyshev.alexandr.presentation.mvp.projectslist

import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.mvp.base.BasePresenter
import io.realm.Realm

/**
 * Created by alexandr on 19.01.18.
 */
class ProjectsListPresenter(override var view: ProjectsListView,
                            val projectsListInteractor: ProjectsListInteractor)
    : BasePresenter<ProjectsListView> {

    lateinit var adapter: ProjectListRecyclerViewAdapter

    fun loadProjects() {
        val projects = projectsListInteractor.getProjects()
        adapter = ProjectListRecyclerViewAdapter(projects)
        view.setAdapter(adapter)
    }

    fun addProject(project: Project) {
        projectsListInteractor.saveProject(project)
    }

    fun deleteProject() {

    }
}