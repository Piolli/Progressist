package com.kamyshev.alexandr.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.support.annotation.Dimension
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView

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

    fun dialogChoose(context: Context, title: String, items: Array<String>, onItemClick: (which: Int) -> Unit) {
        val titleView = TextView(context)
        titleView.gravity = Gravity.CENTER_HORIZONTAL
        titleView.text = title

        titleView.setPadding(ImageUtils.dpToPx(context, 8), ImageUtils.dpToPx(context, 20),
                ImageUtils.dpToPx(context, 8), ImageUtils.dpToPx(context, 16))
        titleView.setTextSize(Dimension.SP, 18f)
        titleView.setTextColor(Color.BLACK)

        val dialog = AlertDialog.Builder(context)
                .setCustomTitle(titleView)
                .setItems(items) { dialog, which ->
                    onItemClick.invoke(which)
                    dialog.dismiss()
                }
                .create()

        dialog.show()
    }
}