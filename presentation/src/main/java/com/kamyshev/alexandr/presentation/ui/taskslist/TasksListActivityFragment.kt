package com.kamyshev.alexandr.presentation.ui.taskslist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.presentation.R

class TasksListActivityFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list_fragment)

        val projectKey = intent.getStringExtra(TasksListActivity.EXRTRA_PROJECT_KEY)
        val project = ProjectsListRepositoryImpl().getProjectByID(projectKey)
        val projectTasks = project.tasks.toMutableList()
        val tasksFragment = TasksListFragment.createInstance(projectTasks, projectKey, project.name)

        supportFragmentManager.beginTransaction()
                .add(R.id.frag_container, tasksFragment)
                .commit()
    }
}
