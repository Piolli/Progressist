package com.kamyshev.alexandr.presentation.ui.projectslist

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.projectslist.ProjectsListInteractor
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectListRecyclerViewAdapter
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListPresenter
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListView
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListActivity
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListActivityFragment
import com.muddzdev.styleabletoastlibrary.StyleableToast
import com.orhanobut.logger.Logger
import hugo.weaving.DebugLog
import kotlinx.android.synthetic.main.activity_projects_list.*
import kotlinx.android.synthetic.main.bottom_sheet_add_project.*
//TODO("Refresh project status")
class ProjectsListActivity : Activity(), ProjectsListView {
    val CLASS_NAME = this.javaClass.simpleName

    companion object {
        const val FRAGMENT_TASK_RESULT_ID = 0x1
        const val EXTRA_IS_CHANGE_TASK_BOOLEAN = "change_tasks"
    }

    private lateinit var presenter: ProjectsListPresenter
    private lateinit var dialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects_list)

        setupRecyclerView()
        setupBottomSheetView()

        presenter = ProjectsListPresenter(this, ProjectsListInteractor(ProjectsListRepositoryImpl()))
        presenter.loadProjects()

    }

    private fun setupRecyclerView() {
        projectListRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
    }

    private fun setupBottomSheetView() {
        dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_add_project)

        initProjectColorPicker()

        initCreateProjectFab()

    }

    override fun startActivityIntent(intent: Intent, className: Class<*>) {
        intent.setClass(this, className)
        val newIntent = Intent(this, TasksListActivityFragment::class.java)
        newIntent.putExtra(TasksListActivity.EXRTRA_PROJECT_KEY, intent.getStringExtra(TasksListActivity.EXRTRA_PROJECT_KEY))
        startActivityForResult(newIntent, FRAGMENT_TASK_RESULT_ID)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        Log.d("RESULT", "onActivityResult $requestCode, $resultCode")
////        if(resultCode == RESULT_OK && requestCode == FRAGMENT_TASK_RESULT_ID) {
////            presenter.loadProjects()
////        }
//    }

    override fun onRestart() {
        val projects = presenter.getProjects()
        presenter.adapter.collection.clear()
        presenter.adapter.collection.addAll(projects)
        presenter.adapter.notifyDataSetChanged()
        super.onRestart()
    }


    private fun initCreateProjectFab() {
        create_new_task_fab.setOnClickListener {
            if(!dialog.isShowing)
                dialog.show()
        }

        dialog.bottom_sheet_add_project_button.setOnClickListener {
            if(dialog.isShowing) {
                val projectName = dialog.bottom_sheet_project_name.text.toString()
                //                    val projectDesc = bottom_sheet_desc_project.text.toString()

                if (!projectName.isEmpty()) {
                    val addingProject = Project(projectName,
                            0, arrayListOf(),
                            bgColor = dialog.color_picker_project.selectedColor)

                    presenter.addProject(addingProject)
//                    showMessage("Проект добавлен", ProjectsListView.MessageStyle.COMPLETE)

                    Snackbar.make(create_new_task_fab, "Проекта добавлен.", Snackbar.LENGTH_LONG)
                            .setAction("Добавить задачи") {
                                presenter.onClickProject(addingProject.key)
                            }
                            .show()

                    dialog.bottom_sheet_project_name.text.clear()
                    dialog.dismiss()


                }
                else {
                    showMessage("Неправильно введено название проекта", ProjectsListView.MessageStyle.ERROR)
                }
            }
        }

        dialog.bottom_sheet_close_button.setOnClickListener {
            if(dialog.isShowing) {
                dialog.bottom_sheet_project_name.text.clear()
                dialog.dismiss()
            }

        }
    }

    private fun initProjectColorPicker() {
        dialog.color_picker_project.setInitialColor(Color.parseColor("#286fb9"), true)
        dialog.color_picker_project.addOnColorSelectedListener {
            dialog.bottom_sheet_down_layout.background = ColorDrawable(it)
        }
    }

    override fun scrollListProjectsToTop() {
        projectListRecyclerView.scrollToPosition(0)
    }

    override fun setAdapter(adapter: ProjectListRecyclerViewAdapter) {
        projectListRecyclerView.adapter = adapter
        projectListRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun showMessage(message: String, style: ProjectsListView.MessageStyle) {
        val cornerRadius = 3

        when(style) {
            ProjectsListView.MessageStyle.ERROR -> {
                StyleableToast.Builder(this)
                        .text(message)
                        .textColor(Color.WHITE)
                        .backgroundColor(ContextCompat.getColor(this, R.color.toast_error_color))
                        .cornerRadius(cornerRadius)
                        .show()
            }
            ProjectsListView.MessageStyle.NEUTRAL -> {
                StyleableToast.Builder(this)
                        .text(message)
                        .textColor(Color.WHITE)
                        .backgroundColor(ContextCompat.getColor(this, R.color.toast_neutral_color))
                        .cornerRadius(cornerRadius)
                        .show()
            }
            ProjectsListView.MessageStyle.COMPLETE -> {
                StyleableToast.Builder(this)
                        .text(message)
                        .textColor(Color.WHITE)
                        .backgroundColor(ContextCompat.getColor(this, R.color.toast_complete_color))
                        .cornerRadius(cornerRadius)
                        .show()
            }
        }
    }

    override fun showProgressDialog(enable: Boolean) {

    }

    override fun showProjects(projects: List<Project>) {
        Logger.d("$CLASS_NAME, projects: ${projects.joinToString { it.toString() }}")
    }
}
