package com.kamyshev.alexandr.presentation.mvp.projectslist

import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.presentation.mvp.base.IView

/**
 * Created by alexandr on 19.01.18.
 */
interface ProjectsListView : IView {


    enum class MessageStyle { ERROR, NEUTRAL, COMPLETE }

    /**
     * [style] set style of dialog
     */
    fun showMessage(message: String, style: MessageStyle = MessageStyle.NEUTRAL)

    /**
     * show dialog if [enable] = true
     * close dialog if [enable] = false
     */
    fun showProgressDialog(enable: Boolean)

    /**
     * Show projects in table on screen
     */
    fun showProjects(projects: List<Project>)
}