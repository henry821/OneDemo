package com.demo.modules.span

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.graphics.drawable.Drawable
import android.util.TypedValue
import com.demo.one.R

/**
 * Description Vip等级
 * Author henry
 * Date   2022/12/19
 */
class VipDrawable(private val context: Context) : Drawable() {

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_span_icon3)
    private val paint = Paint()
    private val fontMetrics: FontMetrics

    init {
        with(paint) {
            color = Color.WHITE
            isAntiAlias = true
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                8f,
                context.resources.displayMetrics
            )
        }
        fontMetrics = paint.fontMetrics

    }

    override fun draw(canvas: Canvas) {
        val baseLine =
            (intrinsicHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        canvas.drawText("长老", 38f, baseLine, paint)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun getIntrinsicWidth(): Int {
        return bitmap.width
    }

    override fun getIntrinsicHeight(): Int {
        return bitmap.height
    }
}