package com.demo.modules.gallery.crop

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatImageView
import java.lang.ref.WeakReference
import kotlin.math.abs

/**
 * Created by Daniel Liao on 2015/7/8.
 */
class CropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(
    context, attrs, defStyleAttr
), ViewTreeObserver.OnGlobalLayoutListener {

    //取得Matrix的值
    private val matrixValues = FloatArray(9)
    private var currRotate = 0
    private var translateX = 0f
    private var translateY = 0f
    private var isFling = false
    private var isReback = false
    private var isScale = false
    private var drawableWidth = 0f
    private var drawableHeight = 0f

    //图片上层透明框
    private var coverHeight = 0

    //第一次加载判断，配合OnGlobalLayoutListener
    private var onceLoad = true

    //全局 handler
    private val handler = MyHandler(this)

    //缩放比例
    private var minScale = 0f
    private var midScale = 0f
    private var maxScale = 0f
    private var initScale = 0f

    //全局matrix
    private val matrix = Matrix()

    //手势操作
    private val gestureDetector = GestureDetector(context, MyGestureListener())
    private val scaleGestureDetector = ScaleGestureDetector(context, MyScaleGestureListener())

    //控件宽高
    private var viewWidth = 0
    private var viewHeight = 0

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    @Suppress("deprecation")
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeGlobalOnLayoutListener(this)
    }

    override fun onGlobalLayout() {

        //因爲他會調用两次
        if (!onceLoad) {
            return
        }
        onceLoad = false
        super.setScaleType(ScaleType.MATRIX)
        val drawable = drawable ?: return
        viewWidth = width
        viewHeight = height
        drawableWidth = drawable.intrinsicWidth.toFloat()
        drawableHeight = drawable.intrinsicHeight.toFloat()
        coverHeight = (viewHeight - viewWidth) / 2

        /*
         * 计算缩放
         */
        // 小于屏幕的图片会被撑满屏幕
        initScale = if (drawableWidth <= drawableHeight) { // 竖图片
            viewWidth.toFloat() / drawableWidth
        } else { // 横图片
            viewWidth.toFloat() / drawableHeight
        }
        matrix.postScale(initScale, initScale)
        maxScale = initScale * 3.0f
        midScale = initScale * 2f
        minScale = initScale * 0.5f

        /*
         * 计算初始位置
         */if (drawableWidth <= drawableHeight) { // 竖图片
            translateX = 0f
            translateY = (viewHeight - drawableHeight * initScale) / 2.0f
        } else {
            translateX = (viewWidth - drawableWidth * initScale) / 2.0f
            translateY = (viewHeight - viewWidth) / 2.0f
        }
        matrix.postTranslate(translateX, translateY)

//        try {
//            initForeBitmap();
//        } catch (Throwable throwable) {
//        }
        handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
    }

    fun canClip(): Boolean {
        return !(isScale || isFling || isReback)
    }

    /**
     * 旋转
     */
    fun rotate() {
        if (isScale || isFling || isReback) {
            return
        }
        currRotate += 90
        if (currRotate == 360) {
            currRotate = 0
        }
        val valueAnimator = ValueAnimator.ofInt(0, 90)
        valueAnimator.setDuration(300)
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            var pre = Int.MAX_VALUE
            override fun onAnimationUpdate(animation: ValueAnimator) {
                try {
                    val animatedValue = animation.animatedValue as Int
                    if (pre == Int.MAX_VALUE) {
                        pre = animatedValue
                    }
                    matrix.postRotate(
                        (animatedValue - pre).toFloat(),
                        (viewWidth / 2).toFloat(),
                        (viewHeight / 2).toFloat()
                    )
                    handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
                    pre = animatedValue
                } catch (ignore: Throwable) {
                }
            }
        })
        valueAnimator.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var retVal = false
        try {

            //如果是手指抬起的操作
            if (event.action == MotionEvent.ACTION_UP) {
                val rectF = matrixRectF
                if (rectF.left > 0 || rectF.right < viewWidth || rectF.top > coverHeight || rectF.bottom < viewHeight - coverHeight) {
                    if (!(currScale + 0.001 < initScale || currScale > midScale)) { //沒有縮放
                        reback()
                    }
                }
            }
            retVal = gestureDetector.onTouchEvent(event)
            retVal = scaleGestureDetector.onTouchEvent(event) || retVal
        } catch (ignore: Throwable) {
        }

        // 返回
        return retVal || super.onTouchEvent(event)
    }

    val currScale: Float
        /**
         * 取得当前缩放比例
         *
         * @return
         */
        get() {
            matrix.getValues(matrixValues)
            var scaleXIndex = 0
            if (currRotate == 0 || currRotate == 180) {
                scaleXIndex = 0
            } else if (currRotate == 90 || currRotate == 270) {
                scaleXIndex = 3
            }

            // 返回
            return abs(matrixValues[scaleXIndex])
        }
    private val matrixRectF: RectF
        /**
         * 获得图片放大、缩小以后的宽高，以及left、top、Right、Bottom
         *
         * @return
         */
        get() {
            val rectF = RectF()
            val d = drawable
            if (d != null) {
                rectF[0f, 0f, d.intrinsicWidth.toFloat()] = d.intrinsicHeight.toFloat()
                matrix.mapRect(rectF)
            }

            // 返回
            return rectF
        }

    /**
     * 剪切图片，返回剪切后的bitmap对象
     *
     * @return
     */
    fun clip(): Bitmap? {
        return if (isScale || isFling || isReback) {
            null
        } else try {
            val width = width
            val height = height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            draw(canvas)
            val b = Bitmap.createBitmap(bitmap, 0, (height - width) / 2, width, width)
            bitmap.recycle()
            b
        } catch (e: Throwable) {
            null
        }
    }

    private fun fling(velocityX: Float, velocityY: Float) {
        var dx = velocityX
        var dy = velocityY
        isFling = true
        val rectF = matrixRectF
        if (rectF.left < 0 && rectF.right > viewWidth) {

            //往左边滑动
            if (dx < 0 && abs(dx) > rectF.right - viewWidth) {
                dx = -(rectF.right - viewWidth)
            }
            //往右边滑动
            if (dx > 0 && dx > abs(rectF.left)) {
                dx = abs(rectF.left)
            }
            val valueAnimator = ValueAnimator.ofFloat(0f, dx)
            valueAnimator.setDuration(300)
            valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                var pre = Int.MAX_VALUE.toFloat()
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    try {
                        val animatedValue = animation.animatedValue as Float
                        if (pre == Int.MAX_VALUE.toFloat()) {
                            pre = animatedValue
                        }
                        matrix.postTranslate(animatedValue - pre, 0f)
                        handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
                        pre = animatedValue
                    } catch (ignore: Throwable) {
                    }
                }
            })
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isFling = false
                }
            })
            valueAnimator.start()
        }
        if (rectF.top < coverHeight && rectF.bottom > viewHeight - coverHeight) {

            //往上边滑动
            if (dy < 0 && abs(dy) > rectF.bottom - (viewHeight - coverHeight)) {
                dy = -(rectF.bottom - (viewHeight - coverHeight))
            }
            //往下边滑动
            if (dy > 0 && dy > abs(coverHeight - rectF.top)) {
                dy = abs(coverHeight - rectF.top)
            }
            val valueAnimator = ValueAnimator.ofFloat(0f, dy)
            valueAnimator.setDuration(300)
            valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                var pre = Int.MAX_VALUE.toFloat()
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    try {
                        val animatedValue = animation.animatedValue as Float
                        if (pre == Int.MAX_VALUE.toFloat()) {
                            pre = animatedValue
                        }
                        matrix.postTranslate(0f, animatedValue - pre)
                        handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
                        pre = animatedValue
                    } catch (ignore: Exception) {
                    }
                }
            })
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isFling = false
                }
            })
            valueAnimator.start()
        }
    }

    private fun reback() {
        var velocityX = 0f
        var velocityY = 0f
        isReback = true
        val rectF = matrixRectF
        if (rectF.left > 0) {
            velocityX = -rectF.left
        } else if (rectF.right < viewWidth) {
            velocityX = viewWidth - rectF.right
        }
        if (rectF.top > coverHeight) {
            velocityY = -(rectF.top - coverHeight)
        } else if (rectF.bottom < viewHeight - coverHeight) {
            velocityY = viewHeight - coverHeight - rectF.bottom
        }
        if (velocityX != 0f) {
            val valueAnimator = ValueAnimator.ofFloat(0f, velocityX)
            valueAnimator.setDuration(300)
            valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                var pre = Int.MAX_VALUE.toFloat()
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    try {
                        val animatedValue = animation.animatedValue as Float
                        if (pre == Int.MAX_VALUE.toFloat()) {
                            pre = animatedValue
                        }
                        matrix.postTranslate(animatedValue - pre, 0f)
                        handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
                        pre = animatedValue
                    } catch (ignore: Exception) {
                    }
                }
            })
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isReback = false
                }
            })
            valueAnimator.start()
        }
        if (velocityY != 0f) {
            val valueAnimator = ValueAnimator.ofFloat(0f, velocityY)
            valueAnimator.setDuration(300)
            valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                var pre = Int.MAX_VALUE.toFloat()
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    try {
                        val animatedValue = animation.animatedValue as Float
                        if (pre == Int.MAX_VALUE.toFloat()) {
                            pre = animatedValue
                        }
                        matrix.postTranslate(0f, animatedValue - pre)
                        handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
                        pre = animatedValue
                    } catch (ignore: Throwable) {
                    }
                }
            })
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isReback = false
                }
            })
            valueAnimator.start()
        }
        if (velocityX == 0f && velocityY == 0f) {
            isReback = false
        }
    }

    private fun baseRectF(rectF: RectF): RectF {

        //默认是正方形
        val baseRectF = RectF(
            0f,
            coverHeight.toFloat(),
            viewWidth.toFloat(),
            (coverHeight + viewWidth).toFloat()
        )
        val imgWidth: Float
        val imgHeight: Float
        if (currRotate == 0 || currRotate == 180) {
            imgWidth = drawableWidth * initScale
            imgHeight = drawableHeight * initScale
        } else {
            imgWidth = drawableHeight * initScale
            imgHeight = drawableWidth * initScale
        }
        if (imgWidth > imgHeight) { //横图
            if (rectF.left > 0) {
                baseRectF.left = 0f
                baseRectF.right = imgWidth
            } else {
                baseRectF.left = viewWidth - imgWidth
                baseRectF.right = viewWidth.toFloat()
            }
        } else if (imgWidth < imgHeight) { //竖图
            if (rectF.top > coverHeight) {
                baseRectF.top = coverHeight.toFloat()
                baseRectF.bottom = imgHeight + coverHeight
            } else {
                baseRectF.top = coverHeight + viewWidth - imgHeight
                baseRectF.bottom = (coverHeight + viewWidth).toFloat()
            }
        }
        return baseRectF
    }

    private fun calcScaleOffsetX(rectF: RectF): Float {
        if (rectF.left < 0 && rectF.right > viewWidth) {
            return (viewWidth / 2).toFloat()
        }
        val baseRectF = baseRectF(rectF)
        val left = baseRectF.left - rectF.left
        val right = rectF.right - baseRectF.right

        //比例
        val ratio = left / (left + right)
        return baseRectF.width() * ratio + baseRectF.left
    }

    private fun calcScaleOffsetY(rectF: RectF): Float {
        if (rectF.top < coverHeight && rectF.bottom > viewHeight - coverHeight) {
            return (viewHeight / 2).toFloat()
        }
        val baseRectF = baseRectF(rectF)
        val top = baseRectF.top - rectF.top
        val bottom = rectF.bottom - baseRectF.bottom

        //比例
        val ratio = top / (top + bottom)
        return baseRectF.height() * ratio + baseRectF.top
    }

    fun scaleEnd(startScale: Float, endScale: Float) {
        isScale = true
        val rectF = matrixRectF
        val offsetX = calcScaleOffsetX(rectF)
        val offsetY = calcScaleOffsetY(rectF)
        val valueAnimator = ValueAnimator.ofFloat(startScale, endScale)
        valueAnimator.setDuration(300)
        valueAnimator.addUpdateListener { animation ->
            try {
                val animatedValue = animation.animatedValue as Float
                val dx: Float
                val dy: Float
                if (startScale > endScale) {
                    dx = (viewWidth / 2).toFloat()
                    dy = (viewHeight / 2).toFloat()
                } else {
                    dx = offsetX
                    dy = offsetY
                }
                matrix.postScale(animatedValue / currScale, animatedValue / currScale, dx, dy)
                handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
            } catch (ignore: Throwable) {
            }
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isScale = false
                reback()
            }
        })
        valueAnimator.start()
    }

    /**
     * 缩放手势监听器
     */
    internal inner class MyScaleGestureListener :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            try {
                val currScale: Float = currScale
                var scaleFactor = detector.scaleFactor

                //不能超过边界
                if (currScale < maxScale && scaleFactor > 1.0f || currScale > minScale && scaleFactor < 1.0f) {
                    if (scaleFactor * currScale < minScale) {
                        scaleFactor = minScale / currScale
                    }
                    if (scaleFactor * currScale > maxScale) {
                        scaleFactor = maxScale / currScale
                    }
                    matrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                }
                handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)
            } catch (ignore: Throwable) {
            }

            // 返回
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            super.onScaleEnd(detector)
            if (currScale < initScale) {
                scaleEnd(currScale, initScale)
            } else if (currScale > midScale) {
                scaleEnd(currScale, midScale)
            }
        }
    }

    /**
     * 全局Handler
     */
    private class MyHandler(view: CropView) : Handler(Looper.getMainLooper()) {
        private val ref = WeakReference(view)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == HANDLE_MESSAGE_INVALIDATE) {
                try {
                    ref.get()?.imageMatrix = ref.get()?.matrix
                } catch (ignore: Throwable) {
                }
            }
        }
    }

    /**
     * 滚动手势监听器
     */
    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            if (isScale || isFling || isReback) {
                return true
            }
            matrix.postTranslate(-distanceX, -distanceY)

            //刷新界面
            handler.sendEmptyMessage(HANDLE_MESSAGE_INVALIDATE)

            // 返回
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            val rectF: RectF = matrixRectF
            if (rectF.left < 0 && rectF.right > viewWidth || rectF.top < coverHeight && rectF.bottom > viewHeight - coverHeight) { //满足Fling条件
                if (!(currScale < initScale || currScale > midScale)) { //沒有縮放。
                    fling(velocityX / 10, velocityY / 10)
                }
            }
            return true
        }
    }

    companion object {
        private const val HANDLE_MESSAGE_INVALIDATE = 1
    }
}
