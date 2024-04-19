package com.demo.modules.gallery.crop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * Created by Jony on 2016/7/18.
 */
class CropCoverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDensity = 0f
    private var mBitmap: Bitmap? = null
    private var isFromAvatar = true

    init {
        initContext(context)
    }

    private fun initContext(context: Context) {
        setBackgroundColor(Color.TRANSPARENT)
        mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        mDensity = context.resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBitmap == null) {
            initBitmap(width, height)
        }
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap!!, 0f, 0f, null)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initBitmap(w, h)
    }

    private fun initBitmap(w: Int, h: Int) {
        try {
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(mBitmap!!)
                canvas.drawColor(Color.argb(126, 0, 0, 0))
                if (isFromAvatar) {
                    canvas.drawCircle(
                        (w / 2).toFloat(),
                        (h / 2).toFloat(),
                        min(w, h) / 2 - 1 * mDensity,
                        mPaint
                    )
                    resetPaint()
                    canvas.drawCircle(
                        (w / 2).toFloat(),
                        (h / 2).toFloat(),
                        min(w, h) / 2 - 2 * mDensity,
                        mPaint
                    )
                } else {
                    val top = (h - w) / 2
                    val rect = Rect(0, top, w, h - top)
                    canvas.drawRect(rect, mPaint)
                    resetPaint()
                    rect.left = (rect.left + 1 * mDensity).toInt()
                    rect.right = (rect.right - 1 * mDensity).toInt()
                    canvas.drawRect(rect, mPaint)
                }
            }
        } catch (ignore: Throwable) {
        }
    }

    private fun resetPaint() {
        mPaint.reset()
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mDensity * 1
        mPaint.color = Color.WHITE
    }

    fun setFromAvatar(fromTag: Boolean) {
        isFromAvatar = fromTag
    }
}
