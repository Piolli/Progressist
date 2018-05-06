package com.kamyshev.alexandr.presentation.ui.taskslist

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.presentation.R


class TasksListActivity : AppCompatActivity() {
    companion object {
        val EXRTRA_PROJECT_KEY = "PROJECT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.tasks_actvity)

//        Log.d("TasksListActivity", "onCreate TasksListActivity")
//
//        val projectKey = intent.getStringExtra(EXRTRA_PROJECT_KEY)
//        val project = ProjectsListRepositoryImpl().getProjectByID(projectKey)
//        val projectTasks = project.tasks.toMutableList()
//
//        val tasksFragment = TasksListFragment.createInstance(projectTasks, projectKey, project.name)
//
//        supportFragmentManager.beginTransaction()
//                .add(R.id.container, tasksFragment, "TAG")
//                .commitNow()

    }
}