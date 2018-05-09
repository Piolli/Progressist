package com.kamyshev.alexandr.presentation.ui.custom

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.graphics.drawable.VectorDrawableCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.kamyshev.alexandr.presentation.R
import java.util.*

class TaskProgressView(val mcontext: Context, val attributeSet: AttributeSet) : View(mcontext, attributeSet) {
    val LOG = TaskProgressView::class.java.simpleName

    val completeVectorDrawable = VectorDrawableCompat.create(mcontext.resources, R.drawable.ic_check_black_24dp, null)
    val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val backPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val frontPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val outlinePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Center of the circle
    var cx = 0f
    var cy = 0f

    var maxValue = 1
        set(value) {
            field = value
            invalidate()
        }

    var currentValue = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
        backPaint.color = Color.parseColor("#EAEAEA")
        backPaint.style = Paint.Style.FILL

        frontPaint.color = Color.parseColor("#90D682")
        frontPaint.style = Paint.Style.FILL

        outlinePaint.color = Color.parseColor("#676767")
        outlinePaint.style = Paint.Style.STROKE
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Calculate center
        if(cx == 0f || cy == 0f) {
            cx = (width / 2) * 1f
            cy = (height / 2) * 1f
            Log.d(LOG, "cx: $cx, cy: $cy")
            Log.d(LOG, "width: $width, height: $height")
        }

        Log.d(LOG, "currentValue: $currentValue")
        Log.d(LOG, "maxValue: $maxValue")
        if(maxValue == 0)
            maxValue = 1

        val currentValueInProgress = (currentValue*1f)/maxValue
        Log.d(LOG, "currentValueInProgress: $currentValueInProgress")

        canvas?.apply {
            drawCircle(cx, cy, cx, backPaint)
            drawCircle(cx, cy, cx*currentValueInProgress, frontPaint)
//        canvas?.drawCircle(cx, cy, cx*0.85f, outlinePaint)
        }

        // If task is complete show check asset
        if(currentValueInProgress.toInt() == 1) {
            completeVectorDrawable?.setBounds(0,0, canvas?.width!!/2, canvas?.height/2)
//            canvas?.translate(cx + completeVectorDrawable!!.intrinsicWidth, cy+completeVectorDrawable.intrinsicHeight)

            canvas?.translate(canvas?.width!!/4f, canvas?.height/4f)
            completeVectorDrawable?.draw(canvas)
//            canvas?.drawBitmap(checkBitmap, cx, cy, bitmapPaint)
        }
    }


}