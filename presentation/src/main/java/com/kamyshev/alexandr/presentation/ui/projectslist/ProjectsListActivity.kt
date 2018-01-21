package com.kamyshev.alexandr.presentation.ui.projectslist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.data.repositories.repository.RepositoryProvider
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListPresenter
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListView
import java.util.*
import kotlin.collections.ArrayList

class ProjectsListActivity : Activity(), ProjectsListView {
    val LOG = this.javaClass.simpleName

    lateinit var presenter: ProjectsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects_list)
        presenter = ProjectsListPresenter(this, ProjectsListInteractor(ProjectsListRepositoryImpl()))
        presenter.loadProjects()


        val taskList: ArrayList<Task> = arrayListOf()
        val subtaskList: ArrayList<SubTask> = arrayListOf()

        subtaskList.add(SubTask("SubTask", true))
        taskList.add(Task("First part", 10, subtaskList))

        presenter.addProject(Project("Disign", 10, taskList))
    }

    override fun showMessage(message: String, style: ProjectsListView.MessageStyle) {

    }

    override fun showProgressDialog(enable: Boolean) {

    }

    override fun showProjects(projects: List<Project>) {
        Log.d(LOG, projects.toString())
    }
}
