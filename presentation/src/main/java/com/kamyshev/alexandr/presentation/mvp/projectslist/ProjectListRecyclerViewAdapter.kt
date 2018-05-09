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
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListFragment
import com.kamyshev.alexandr.presentation.utils.DialogFactory
import io.realm.Realm
import kotlinx.android.synthetic.main.project_list_item.view.*
import java.util.*

/**
 * Created by alexandr on 27.01.18.
 */
class ProjectListRecyclerViewAdapter(val collection: MutableList<Project>, val onDeleteProject: onDeleteProject, val onClickListener: OnClickProjectListener)
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

    fun addProject(project: Project) {
        collection.add(0, project)
        notifyItemInserted(0)
    }


    inner class ProjectViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(project: Project) {
            project.let {
                view.projectTitleView.text = it.name
                view.projectCountCompleteTasks.text =
                        view.resources.getString(R.string.project_count_completed_tasks, it.progressCount, it.tasks.size)
                view.projectProgressBar.progress = it.progress
                view.setBackgroundColor(it.bgColor)
                view.projectProgressBar.max = it.tasks.size
                view.projectProgressBar.progress = it.progressCount
                view.setOnClickListener {
                    onClickListener.onClickProject(project.key)
                }
                view.setOnLongClickListener {
                    DialogFactory.dialog2(view.context, "Удалить проект?", "Да", "Отмена") {
                        if(it == DialogFactory.ANSWER.POSITIVE) {
                            onDeleteProject.onDelete(project.key)
                            collection.remove(project)
                            notifyItemRemoved(adapterPosition)
                        }
                    }


                    true
                }
            }
        }
    }

}