package com.demo.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.demo.one.R

/**
 * Created by hengwei on 2022/9/19.
 */
class DisplayShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : GLSurfaceView(context, attrs) {

    private val renderer: DisplayShapeRenderer

    init {
        //设置版本号
        setEGLContextClientVersion(3)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DisplayShapeView)
        val shapeStr = typedArray.getString(R.styleable.DisplayShapeView_display_shape)
        typedArray.recycle()

        //创建Renderer
        renderer = DisplayShapeRenderer(shapeStr)
        setRenderer(renderer)

        //渲染模式设置为 仅在调用 requestRender() 时才渲染，减少不必要的绘制
        renderMode = RENDERMODE_WHEN_DIRTY
    }

}