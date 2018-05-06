package com.kamyshev.alexandr.presentation.mvp.taskslist

import com.kamyshev.alexandr.presentation.mvp.base.IView

interface TasksListView : IView {
    fun scrollRecyclerViewToPosition(position: Int)
}