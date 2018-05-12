package com.kamyshev.alexandr.presentation.utils

import android.content.Context

object ImageUtils {
    fun dpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        val padding_in_px = (dp * scale + 0.5f).toInt()
        return padding_in_px
    }
}