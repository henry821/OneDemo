package com.demo.modules.surfaceview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.sin

/**
 * Description
 * Author henry
 * Date   2023/3/15
 */
class MySurfaceView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback, Runnable {

    companion object {
        private const val TAG = "MySurfaceView"
    }

    private val surfaceHolder: SurfaceHolder = holder
    private var canvas: Canvas? = null

    @Volatile
    private var canDoDraw = false
    private val drawThread = Thread(this)
    private val lock = Object()

    private var xx = 0f
    private var yy = 400f
    private val path = Path()
    private val paint = Paint()

    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        surfaceHolder.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
        path.moveTo(xx, yy)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.RED
        drawThread.start()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceCreated")
        setCanDraw(true)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(TAG, "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(TAG, "surfaceDestroyed")
        canDoDraw = false
        // 注意当APP到后台时会 destroy Surface, 回到前台会重新调用 surfaceCreated
        // 因此这里不能移除回调，否则会黑屏
        // surfaceHolder.removeCallback(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent")
        setCanDraw(!canDoDraw)
        return super.onTouchEvent(event)
    }

    private fun setCanDraw(canDraw: Boolean) {
        if (canDraw) {
            synchronized(lock) {
                try {
                    lock.notifyAll()
                } catch (_: Exception) {
                }
            }
        }
        canDoDraw = canDraw
    }

    override fun run() {
        while (true) {
            synchronized(lock) {
                if (!canDoDraw) {
                    try {
                        lock.wait()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            draw()
            xx += 1
            yy = (100 * sin(xx * 2 * Math.PI / 180) + 400).toFloat()
            path.lineTo(xx, yy)
        }
    }

    private fun draw() {
        try {
            canvas = surfaceHolder.lockCanvas()
            canvas?.drawColor(Color.WHITE)
            canvas?.drawPath(path, paint)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            canvas?.apply { surfaceHolder.unlockCanvasAndPost(this) }
        }
    }
}