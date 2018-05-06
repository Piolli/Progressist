package com.kamyshev.alexandr.presentation.mvp.projectslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.mvp.base.BasePresenter
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListActivity
import com.orhanobut.logger.Logger
import hugo.weaving.DebugLog

/**
 * Created by alexandr on 19.01.18.
 */
class ProjectsListPresenter(override var view: ProjectsListView,
                            private val projectsListInteractor: ProjectsListInteractor)
    : BasePresenter<ProjectsListView>, onDeleteProject, OnClickProjectListener {

    val CLASS_NAME = ProjectsListPresenter::class.java.simpleName

    private lateinit var adapter: ProjectListRecyclerViewAdapter

    fun loadProjects() {
        val projects = projectsListInteractor.getProjects()
        adapter = ProjectListRecyclerViewAdapter(projects.toMutableList(), this, this)
        view.setAdapter(adapter)
    }

    fun addProject(project: Project) {
        projectsListInteractor.addProject(project)
        adapter.addProject(project)
        view.scrollListProjectsToTop()
    }


    override fun onDelete(key: String) {
        projectsListInteractor.deleteProjectByID(key)
    }

    override fun onClickProject(key: String) {
        Log.d(CLASS_NAME, "$CLASS_NAME, key: $key")
        val intent = Intent()
        intent.putExtra(TasksListActivity.EXRTRA_PROJECT_KEY, key)
        view.startActivityIntent(intent, TasksListActivity::class.java)
    }
}

interface onDeleteProject {
    fun onDelete(key: String)
}

interface OnClickProjectListener {
    fun onClickProject(key: String)
}
