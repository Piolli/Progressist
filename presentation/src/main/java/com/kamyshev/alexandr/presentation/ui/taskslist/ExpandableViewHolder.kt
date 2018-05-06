package com.kamyshev.alexandr.presentation.ui.taskslist

import android.animation.ValueAnimator
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout

class ExpandableViewHolder(val view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener {

    private var originalHeight = 0
    private var isViewExpanded = false
    private lateinit var expandView: LinearLayout

    init {
        view.setOnClickListener(this)

        if(!isViewExpanded) {
            expandView.visibility = View.GONE
            expandView.isEnabled = false
        }
    }

    override fun onClick(v: View?) {
        if(originalHeight == 0) {
            originalHeight = v?.height ?: 0
        }

        var valueAnimator: ValueAnimator

        if(!isViewExpanded) {
            expandView.visibility = View.VISIBLE
            expandView.isEnabled = true
            isViewExpanded = true
        }
    }
}
