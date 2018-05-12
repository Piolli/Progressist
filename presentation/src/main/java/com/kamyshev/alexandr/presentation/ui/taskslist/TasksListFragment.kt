package com.kamyshev.alexandr.presentation.ui.taskslist

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.kamyshev.alexandr.data.repositories.TasksListRepositoryImpl
import com.kamyshev.alexandr.domain.global.models.Task
import com.kamyshev.alexandr.domain.global.taskslist.TasksListInteractor
import com.kamyshev.alexandr.presentation.R
import com.kamyshev.alexandr.presentation.mvp.projectslist.ProjectsListView
import com.kamyshev.alexandr.presentation.mvp.taskslist.TasksListPresenter
import com.kamyshev.alexandr.presentation.mvp.taskslist.TasksListView
import com.muddzdev.styleabletoastlibrary.StyleableToast
import com.orhanobut.logger.Logger
import hugo.weaving.DebugLog
import io.realm.RealmList
import kotlinx.android.synthetic.main.dialog_create_task.view.*
import kotlinx.android.synthetic.main.tasks_list_fragment.*
import nl.dionsegijn.steppertouch.StepperTouch
import android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags
import android.support.v7.widget.helper.ItemTouchHelper.LEFT
import android.support.v7.widget.helper.ItemTouchHelper.RIGHT
import com.kamyshev.alexandr.domain.global.models.SubTask
import kotlinx.android.synthetic.main.dialog_create_sub_task.view.*


class TasksListFragment :
        Fragment(), TasksListView, TasksListAdapter.OnDeleteTask,
        TasksListAdapter.OnAddSubTask, SubTasksListAdapter.OnDeleteSubTask,
        SubTasksListAdapter.OnChangeCheckedSubTask,
        TasksListAdapter.OnChangeTask
{

    lateinit var tasks: MutableList<Task>
    lateinit var projectKey: String
    lateinit var projectName: String
    lateinit var presenter: TasksListPresenter
    lateinit var adapter: TasksListAdapter
    lateinit var isChangedCallback: TasksListActivityFragment.IsChangeTaskCallback

    companion object {
        val CLASS_NAME = TasksListFragment::class.java.simpleName

        fun createInstance(tasks: MutableList<Task>, projectKey: String, projectName: String,
                           isChangedCallback: TasksListActivityFragment.IsChangeTaskCallback): TasksListFragment {
            val fragment = TasksListFragment()
            Logger.d("tasks %s\n projectKey %s\n projectName %s\n", tasks, projectKey, projectName)
            fragment.tasks = tasks
            fragment.projectKey = projectKey
            fragment.projectName = projectName
            fragment.isChangedCallback = isChangedCallback
            Log.d(CLASS_NAME, "$CLASS_NAME: createInstance(${tasks.joinToString()})")
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.tasks_list_fragment, container, false)
        Logger.d("$CLASS_NAME: onCreateView()")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("$CLASS_NAME: onViewCreated()")
        adapter = TasksListAdapter(tasks, this, this, this, this, this)
        presenter = TasksListPresenter(this, projectKey, TasksListInteractor(TasksListRepositoryImpl()), adapter)
        title_project_view.text = projectName
        initRecyclerView()
        initFABcreateTask()
    }


    fun initFABcreateTask() {
        create_new_task_fab.setOnClickListener {
            createTaskDialog()
        }
    }

    //On Add sub task
    override fun onClick(task: Task, subTask: SubTask) {
        presenter.addSubTask(task, subTask)
        isChangedCallback.invoke()
    }

    override fun onDeleteSubTask(task: Task, subTask: SubTask) {
        presenter.deleteSubTask(task, subTask)
        isChangedCallback.invoke()
    }

    override fun scrollRecyclerViewToPosition(position: Int) {
        tasks_recycler_view.scrollToPosition(position)
    }

    override fun onDelete(task: Task, position: Int) {
        presenter.deleteTasks(task)
        adapter.data.remove(task)
        adapter.notifyItemRemoved(position)
        isChangedCallback.invoke()
    }

    override fun onChangeCheckedSubTask(task: Task, subTask: SubTask, isChecked: Boolean) {
        presenter.setCheckedSubTask(task, subTask, projectKey, isChecked)
    }

    override fun onChangeTask(newTask: Task, oldTask: Task) {
        presenter.updateProject(newTask, oldTask)
        val index = adapter.data.indexOf(oldTask)

        val task = Task(newTask.name, oldTask.progress, oldTask.subTasks)
        adapter.data.set(index,task)
        adapter.notifyItemChanged(index)
        isChangedCallback.invoke()
    }

    private fun createTaskDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_task, null, false)
        dialogView.taskPriority.stepper.setMin(1)
        dialogView.taskPriority.stepper.setMax(5)
        dialogView.taskPriority.stepper.setValue(1)

        val dialog = AlertDialog.Builder(context!!)
                .setView(dialogView)
                .setTitle("Добавление задачи в проект")
                .setCancelable(false)
                .setPositiveButton("Добавить") { dialog, which ->
                    val taskName = dialogView.taskNameEditText.text.toString()
                    val taskPriority = dialogView.taskPriority.stepper.getValue()
                    if (!taskName.isEmpty()) {
                        val task = Task(taskName, 0, arrayListOf())
                        presenter.addTask(task)
                        dialog.dismiss()
                    } else {
                        StyleableToast.Builder(context!!)
                                .text("Неправильно введено название задачи")
                                .textColor(Color.WHITE)
                                .backgroundColor(ContextCompat.getColor(context!!, R.color.toast_error_color))
                                .cornerRadius(3)
                                .show()
                    }
                }
                .setNegativeButton("Закрыть") { dialog, which ->
                    dialog.dismiss()
                }
                .create()

        dialog.show()
    }

    private fun initRecyclerView() {
        tasks_recycler_view.layoutManager = LinearLayoutManager(context)
        tasks_recycler_view.adapter = adapter


        val swipeController = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeMovementFlags(0, LEFT or RIGHT)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val deleteTask = adapter.data.get(viewHolder?.adapterPosition ?: 0)
                onDelete(deleteTask, viewHolder?.adapterPosition ?: 0)
            }
        }

        val itemToucHelper = ItemTouchHelper(swipeController)
        itemToucHelper.attachToRecyclerView(tasks_recycler_view)

    }

}