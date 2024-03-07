package com.demo.opengl

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by hengwei on 2022/9/19.
 */
class DisplayShapeRenderer(private val shapeStr: String?) : GLSurfaceView.Renderer {

    private var shape: AbsShape? = null

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //rgba,清空背景
        GLES30.glClearColor(0f, 0f, 0f, 0f)
        //实例化一个三角形
        shape = when (shapeStr) {
            "pentagon" -> Pentagon()
            "rectangle" -> Rectangle()
            "triangle" -> Triangle()
            else -> Triangle()
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //更新可视窗口的大小
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        //清空画面
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        //绘制三角形
        shape?.draw()
    }
}