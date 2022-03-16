package com.demo.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.FrameLayout
import com.demo.utils.dp

/**
 * Created by hengwei on 2022/3/16.
 */
class GestureContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    var onPositionChangedListener: OnPositionChangedListener? = null

    private var gestureDetector = GestureDetector(context, DemoGestureListener())
    private var PointPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, 5f.dp, PointPaint)

    }

    inner class DemoGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            onPositionChangedListener?.onPositionChanged(if (e == null) "null" else MotionEvent.actionToString(
                e.action),
                PointF(e?.x ?: 0f, e?.y ?: 0f))
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            onPositionChangedListener?.onPositionChanged(if (e2 == null) "null" else MotionEvent.actionToString(
                e2.action),
                PointF(e2?.x ?: 0f, e2?.y ?: 0f))
            return true
        }
    }

    interface OnPositionChangedListener {
        fun onPositionChanged(motionEvent: String?, point: PointF)
    }

}