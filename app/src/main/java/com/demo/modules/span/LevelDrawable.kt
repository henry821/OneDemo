package com.demo.modules.span

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import com.demo.one.R

/**
 * Description Vip等级
 * Author henry
 * Date   2022/12/19
 */
class LevelDrawable(private val context: Context) : Drawable() {

    companion object {
        private const val TEXT = "54"
        private const val PADDING_HORIZONTAL = 10f
        private const val PADDING_VERTICAL = 5f
        private const val RADIUS = 30f
    }

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_span_icon1)
    private val bgPaint = Paint()
    private val bgRect: RectF
    private val textPaint = Paint()
    private val fontMetrics: FontMetrics

    init {
        with(bgPaint) {
            color = Color.GREEN
            isAntiAlias = true
        }
        with(textPaint) {
            color = Color.WHITE
            isAntiAlias = true
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                8f,
                context.resources.displayMetrics
            )
        }
        fontMetrics = textPaint.fontMetrics
        val textWidth = textPaint.measureText(TEXT)
        val textHeight = fontMetrics.bottom - fontMetrics.top
        bgRect = RectF(
            0f, 0f, PADDING_HORIZONTAL * 3 + bitmap.width + textWidth,
            PADDING_VERTICAL * 2 + textHeight.coerceAtLeast(bitmap.height.toFloat())
        )

    }

    override fun draw(canvas: Canvas) {
        val baseLine =
            (intrinsicHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawRoundRect(bgRect, RADIUS, RADIUS, bgPaint)
        canvas.drawBitmap(bitmap, PADDING_HORIZONTAL, (intrinsicHeight - bitmap.height) / 2f, null)
        canvas.drawText(TEXT, PADDING_HORIZONTAL * 2 + bitmap.width, baseLine, textPaint)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun getIntrinsicWidth(): Int {
        return bgRect.width().toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return bgRect.height().toInt()
    }
}