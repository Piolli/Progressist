package com.kamyshev.alexandr.presentation.mvp.projectslist

import com.kamyshev.alexandr.presentation.mvp.base.IView

/**
 * Created by alexandr on 19.01.18.
 */
interface ProjectsListView : IView {

    //Styles of dialogs
    enum class MessageType { error, neutral, complete }

    /**
     * [type] set style of dialog
     */
    fun showMessage(message: String, type: MessageType = MessageType.neutral)

    /**
     * show dialog if [enable] = true
     * close dialog if [enable] = false
     */
    fun showProgressDialog(enable: Boolean)
}