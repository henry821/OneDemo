package com.demo.widgets.base

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.RelativeLayout
import com.demo.utils.ScreenUtil
import kotlin.math.abs

/**
 * Created by hengwei on 2021/3/9.
 */
open class DragView : RelativeLayout {

    //View所在位置
    private var mLastX: Float = 0f
    private var mLastY: Float = 0f

    //屏幕宽高
    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0

    //View宽高
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    //是否在拖拽中
    private var isDrag = false

    //系统最小滑动距离
    private var mTouchSlop: Int = 0

    private var floatLayoutParams: WindowManager.LayoutParams
    private var mWindowManager: WindowManager

    private var xInScreen: Float = 0f
    private var yInScreen: Float = 0f
    private var xInView: Float = 0f
    private var yInView: Float = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        mScreenWidth = ScreenUtil.getScreenWidth(context)
        mScreenHeight = ScreenUtil.getScreenHeight(context)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        floatLayoutParams = WindowManager.LayoutParams()
    }

    fun show() {
        floatLayoutParams = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.RGBA_8888)
        floatLayoutParams.gravity = Gravity.START or Gravity.TOP
        floatLayoutParams.x = 0
        floatLayoutParams.y = (mScreenHeight * 0.4f).toInt()
        mWindowManager.addView(this, floatLayoutParams)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = ev.rawX
                mLastY = ev.rawY
                yInView = ev.y
                xInView = ev.x
                xInScreen = ev.rawX
                yInScreen = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.rawX - mLastX
                val dy = ev.rawY - mLastY
                if (abs(dx) > mTouchSlop || abs(dy) > mTouchSlop) {
                    isDrag = true
                }
                xInScreen = ev.rawX
                yInScreen = ev.rawY
                mLastX = ev.rawX
                mLastY = ev.rawY
                //拖拽时更新悬浮窗位置
                updateFloatPosition()
            }
            MotionEvent.ACTION_UP -> {
                if (isDrag) {
                    //执行贴边
                    startAnim()
                    isDrag = false
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mWidth == 0) {
            //获取悬浮球宽高
            mWidth = width
            mHeight = height
        }
    }

    /**
     * 更新悬浮窗位置
     */
    private fun updateFloatPosition() {
        val x = xInScreen - xInView
        var y = yInScreen - yInView
        if (y < 0f) {
            y = 0f
        }
        if (y > mScreenHeight - mHeight) {
            y = (mScreenHeight - mHeight).toFloat()
        }
        floatLayoutParams.x = x.toInt()
        floatLayoutParams.y = y.toInt()
        //更新位置
        mWindowManager.updateViewLayout(this, floatLayoutParams)
    }

    /**
     * 执行贴边动画
     */
    private fun startAnim() {
        val valueAnim: ValueAnimator = if (floatLayoutParams.x < mScreenWidth / 2) {
            ValueAnimator.ofInt(floatLayoutParams.x, 0)
        } else {
            ValueAnimator.ofInt(floatLayoutParams.x, (mScreenWidth - mWidth))
        }
        valueAnim.duration = 200
        valueAnim.addUpdateListener { animation ->
            floatLayoutParams.x = animation?.animatedValue as Int
            mWindowManager.updateViewLayout(this@DragView, floatLayoutParams)
        }
        valueAnim.start()
    }
}