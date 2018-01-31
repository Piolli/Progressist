package com.kamyshev.alexandr.presentation.ui.projectslist

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.SubTask
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectListRecyclerViewAdapter
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListPresenter
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListView
import kotlinx.android.synthetic.main.activity_projects_list.*
import java.util.*
import kotlin.collections.ArrayList

class ProjectsListActivity : Activity(), ProjectsListView {
    val LOG = this.javaClass.simpleName

    private lateinit var presenter: ProjectsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects_list)
        setupRecyclerView()

        presenter = ProjectsListPresenter(this, ProjectsListInteractor(ProjectsListRepositoryImpl()))
        presenter.loadProjects()

//        presenter.addProject(Project("Develop progressist project app", 2, arrayListOf(), UUID.randomUUID().toString(), 7457535))
//        presenter.addProject(Project("Designer my application in sketch", 3, arrayListOf(), UUID.randomUUID().toString(), 0x55ba67))
//        presenter.addProject(Project("Super project", 10, arrayListOf(), UUID.randomUUID().toString(), Color.DKGRAY))
//        presenter.addProject(Project("More profit for my debit card sberb", 2, arrayListOf(), UUID.randomUUID().toString(), 0xba5f55))
//        presenter.addProject(Project("Design", 1, arrayListOf(), UUID.randomUUID().toString()))
//        presenter.addProject(Project("Create new application on mac os", 1, arrayListOf(), UUID.randomUUID().toString()))
//        presenter.addProject(Project("Redesign app", 3, arrayListOf(), UUID.randomUUID().toString()))
//        presenter.addProject(Project("Create UML diagramm", 5, arrayListOf(), UUID.randomUUID().toString()))
    }

    fun setupRecyclerView() {
        projectListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
    }

    override fun setAdapter(adapter: ProjectListRecyclerViewAdapter) {
        projectListRecyclerView.adapter = adapter
    }

    override fun showMessage(message: String, style: ProjectsListView.MessageStyle) {

    }

    override fun showProgressDialog(enable: Boolean) {

    }

    override fun showProjects(projects: List<Project>) {
        Log.d(LOG, projects.toString())
    }
}
