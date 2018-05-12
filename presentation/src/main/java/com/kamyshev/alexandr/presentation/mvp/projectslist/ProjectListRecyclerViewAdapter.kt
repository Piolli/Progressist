package com.kamyshev.alexandr.presentation.mvp.projectslist

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.app.AlertDialog
import android.graphics.Color
import android.media.Image
import android.support.v7.widget.DrawableUtils
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ViewUtils
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.ui.taskslist.TasksListFragment
import com.kamyshev.alexandr.presentation.utils.DialogFactory
import com.kamyshev.alexandr.presentation.utils.ImageUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.bottom_sheet_add_project.*
import kotlinx.android.synthetic.main.bottom_sheet_add_project.view.*
import kotlinx.android.synthetic.main.project_list_item.view.*
import java.util.*

/**
 * Created by alexandr on 27.01.18.
 */
class ProjectListRecyclerViewAdapter(val collection: MutableList<Project>,
                                     val onDeleteProject: onDeleteProject,
                                     val onClickListener: OnClickProjectListener,
                                     val onChangeProject: OnChangeProject)
    : RecyclerView.Adapter<ProjectListRecyclerViewAdapter.ProjectViewHolder>() {

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.project_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(collection[position])
    }


    fun addProject(project: Project) {
        collection.add(0, project)
        notifyItemInserted(0)
    }


    inner class ProjectViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(project: Project) {
            project.let {
                view.projectTitleView.text = it.name
                if(it.progressCount == 0 && it.tasks.isEmpty()) {
                    view.projectCountCompleteTasks.text = "Добавьте задачи"
                }
                else {
                    view.projectCountCompleteTasks.text =
                            view.resources.getString(R.string.project_count_completed_tasks, it.progressCount, it.tasks.size)
                }

                view.projectProgressBar.progress = it.progress
                view.setBackgroundColor(it.bgColor)
                view.projectProgressBar.max = it.tasks.size
                view.projectProgressBar.progress = it.progressCount
                view.setOnClickListener {
                    onClickListener.onClickProject(project.key)
                }
                view.setOnLongClickListener {
                    DialogFactory.dialogChoose(view.context, "Действие с проектом", arrayOf("Изменить проект", "Удалить проект")) {
                        when(it) {
                            //Change project
                            0 -> {
                                val dialogView = LayoutInflater.from(view.context).inflate(R.layout.bottom_sheet_add_project, null, false)
                                dialogView.bottom_sheet_project_name.setText(project.name)
                                dialogView.color_picker_project.setInitialColor(Color.parseColor("#286fb9"), true)
                                dialogView.color_picker_project.addOnColorSelectedListener {
                                    dialogView.bottom_sheet_down_layout.background = ColorDrawable(it)
                                }
                                val ctx = view.context
                                dialogView.color_picker_project.setPadding(ImageUtils.dpToPx(ctx, 24), ImageUtils.dpToPx(ctx, 8),
                                        ImageUtils.dpToPx(ctx, 8), ImageUtils.dpToPx(ctx, 8))
                                dialogView.color_picker_project.setColor(project.bgColor, true)
                                dialogView.bottom_sheet_add_project_button.text = "Сохранить"

                                val dialog = AlertDialog.Builder(view.context)
                                        .setView(dialogView)
                                        .create()

                                dialogView.bottom_sheet_add_project_button.setOnClickListener {
                                    val newProject = Project(dialogView.bottom_sheet_project_name.text.toString(),
                                                    0, arrayListOf(),
                                                    bgColor = dialogView.color_picker_project.selectedColor)
                                    onChangeProject.onChangeProject(newProject, project.key)
                                    dialog.dismiss()
                                }

                                dialogView.bottom_sheet_close_button.setOnClickListener {
                                    dialog.dismiss()
                                }

                                dialog.setView(dialogView,0,0,0,0);
                                dialog.show()
                            }
                            //Delete project
                            1 -> {
                                DialogFactory.dialog2(view.context, "Удалить проект?", "Да", "Отмена") {
                                    if(it == DialogFactory.ANSWER.POSITIVE) {
                                        onDeleteProject.onDelete(project.key)
                                        collection.remove(project)
                                        notifyItemRemoved(adapterPosition)
                                    }
                                }
                            }
                        }
                    }



                    true
                }
            }
        }
    }

}