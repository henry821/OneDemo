package com.demo.modules.span

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.style.ImageSpan


/**
 * Description
 * Author henry
 * Date   2022/12/19
 */
class VerticalImageSpan : ImageSpan {

    constructor(context: Context, resourceId: Int) : super(context, resourceId)
    constructor(context: Context, bitmap: Bitmap) : super(context, bitmap)

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val b = drawable
        canvas.save()
        var transY = 0
        //获得将要显示的文本高度 - 图片高度除2 = 居中位置+top(换行情况)
        //获得将要显示的文本高度 - 图片高度除2 = 居中位置+top(换行情况)
        transY = (bottom - top - b.bounds.bottom) / 2 + top
        //偏移画布后开始绘制
        //偏移画布后开始绘制
        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val d = drawable
        val rect: Rect = d.bounds
        if (fm != null) {
            val fmPaint = paint.fontMetricsInt
            //获得文字、图片高度
            val fontHeight = fmPaint.bottom - fmPaint.top
            val drHeight: Int = rect.bottom - rect.top
            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4
            fm.ascent = -bottom
            fm.top = -bottom
            fm.bottom = top
            fm.descent = top
        }
        return rect.right
    }
}