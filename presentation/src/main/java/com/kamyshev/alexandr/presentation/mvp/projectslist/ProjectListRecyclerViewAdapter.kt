package com.kamyshev.alexandr.presentation.mvp.projectslist

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.DrawableUtils
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.presentation.R
import kotlinx.android.synthetic.main.project_list_item.view.*
import java.util.*

/**
 * Created by alexandr on 27.01.18.
 */
class ProjectListRecyclerViewAdapter(val collection: List<Project>)
    : RecyclerView.Adapter<ProjectListRecyclerViewAdapter.ProjectViewHolder>() {

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.project_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectViewHolder?, position: Int) {
        holder?.bind(collection[position])
    }

    class ProjectViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(project: Project) {
            project.let {
                view.projectTitleView.text = it.name
                view.projectCountCompleteTasks.text =
                        view.resources.getString(R.string.project_count_completed_tasks, it.progress, it.tasks.size)
                view.projectProgressBar.progress = Random().nextInt(100) + 1
                view.setBackgroundColor(it.bgColor)
    //                view.projectProgressBar.progress = 100 * it.progress / (if(it.tasks.size == 0) 1 else it.tasks.size)
                view.setOnClickListener { Toast.makeText(view.context, "Click item", Toast.LENGTH_SHORT).show() }
            }
        }
    }
}