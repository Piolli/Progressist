package com.kamyshev.alexandr.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color

object DialogFactory {
    enum class ANSWER { POSITIVE, NEGATIVE, NEUTRAL }

    fun dialog2(context: Context, message: String, positive: String, negative: String, onCloseDialog: (ANSWER) -> Unit) {
//        TODO("Create dialog with two ansers for delete projects")
        val customDialog = AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positive, {dialog, which -> onCloseDialog(ANSWER.POSITIVE) })
                .setNegativeButton(negative, {
                            dialog, which ->  onCloseDialog(ANSWER.NEGATIVE)
                            dialog.dismiss()
                        })
                .create()

        customDialog.setOnShowListener {
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY)
            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY)
        }

         customDialog.show()
    }
}