package com.demo.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.demo.one.R;


public class PressSpeakView extends View {
    /**
     * View宽度
     */
    private int mWidth;
    /**
     * View高度
     */
    private int mHeight;
    /**
     * 圆半径
     */
    private float mRadius;
    /**
     * 圆左边界
     */
    private float mCircleLeftBorder;
    /**
     * 圆右边界
     */
    private float mCircleRightBorder;
    /**
     * 圆上边界
     */
    private float mCircleTopBorder;
    /**
     * 圆下边界
     */
    private float mCircleBottomBorder;
    /**
     * 话筒Bitmap
     */
    private Bitmap mMicBitmap;
    /**
     * 话筒Bitmap绘制区域
     */
    private Rect mMicArea;
    /**
     * 垃圾箱Bitmap
     */
    private Bitmap mTrashCanBitmap;
    /**
     * 垃圾箱Bitmap绘制区域
     */
    private Rect mTrashCanArea;

    /**
     * 记录是否是按住状态
     */
    private boolean isPressed;
    /**
     * 记录是否是可取消语音状态
     */
    private boolean isCancelable;

    private Paint mCirclePaint;
    private ValueAnimator animator;

    private int mGrayColor;

    private MyOnTouchEventListener myTouchListener;

    public PressSpeakView(Context context) {
        this(context, null);
    }

    public PressSpeakView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PressSpeakView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefaultValue(context);
        initPaint();
        initAnimator();
    }

    private void initAnimator() {
        MyAnimatorListener animatorListener = new MyAnimatorListener();
        animator = ValueAnimator.ofFloat(1f, 1.5f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                mRadius = (mWidth / 2 - mWidth / 5) * value;
                invalidate();
            }
        });
        animator.addListener(animatorListener);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    private void initDefaultValue(Context context) {
        isPressed = false;
        isCancelable = false;
        mGrayColor = ContextCompat.getColor(context, R.color.gray_f5f5f5);
        mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        mMicBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_chat_audio_record_blue);
        mTrashCanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_chat_audio_record_cancel);
        mMicArea = new Rect();
        mTrashCanArea = new Rect();
    }

    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mGrayColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(6);
        mCirclePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        mMicArea.left = mTrashCanArea.left = mWidth / 2 - 50;
        mMicArea.right = mTrashCanArea.right = mWidth / 2 + 50;
        mMicArea.top = mTrashCanArea.top = mHeight / 2 - 50;
        mMicArea.bottom = mTrashCanArea.bottom = mHeight / 2 + 50;

        mRadius = mWidth / 2 - mWidth / 5;

        mCircleLeftBorder = mWidth / 2 - mRadius;
        mCircleRightBorder = mWidth / 2 + mRadius;
        mCircleTopBorder = mHeight / 2 - mRadius;
        mCircleBottomBorder = mHeight / 2 + mRadius;

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isPressed) {
            mCirclePaint.setStyle(Paint.Style.STROKE);
        } else {
            mCirclePaint.setStyle(Paint.Style.FILL);
            if (isCancelable) {
                mCirclePaint.setColor(Color.RED);
                canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mCirclePaint);
                canvas.drawBitmap(mTrashCanBitmap, null, mTrashCanArea, null);
                return;
            }
        }
        mCirclePaint.setColor(mGrayColor);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mCirclePaint);
        canvas.drawBitmap(mMicBitmap, null, mMicArea, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断点击区域是否在圆内
                if (event.getX() < mCircleRightBorder
                        && event.getX() > mCircleLeftBorder
                        && event.getY() < mCircleBottomBorder
                        && event.getY() > mCircleTopBorder) {
                    isPressed = true;
                    invalidate();
                    delayStartAnimator();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                isCancelable = (y < mCircleTopBorder)
                        || (y > mCircleBottomBorder)
                        || (x > mCircleRightBorder)
                        || (x < mCircleLeftBorder);
                if (isCancelable) {
                    if (animator.isStarted()) {
                        animator.end();
                    }
                } else {
                    if (!animator.isStarted()) {
                        instantStartAnimator();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isPressed = false;
                isCancelable = false;
                animator.end();
                break;
        }
        return myTouchListener == null || myTouchListener.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mMicBitmap.recycle();
        mTrashCanBitmap.recycle();
        mMicBitmap = null;
        mTrashCanBitmap = null;
        if (animator.isStarted()) {
            animator.cancel();
        }
        animator.removeAllListeners();
        animator.removeAllUpdateListeners();
        animator = null;
    }

    public void setMyTouchListener(MyOnTouchEventListener myTouchListener) {
        this.myTouchListener = myTouchListener;
    }

    /**
     * 以下为私有方法
     */

    private void delayStartAnimator() {
        animator.setStartDelay(500);
        animator.start();
    }

    private void instantStartAnimator() {
        animator.setStartDelay(0);
        animator.start();
    }

    public interface MyOnTouchEventListener {
        boolean onTouchEvent(MotionEvent event);
    }

    private class MyAnimatorListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationStart(Animator animation) {
            mMicArea.setEmpty();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mMicArea.left = mWidth / 2 - 50;
            mMicArea.right = mWidth / 2 + 50;
            mMicArea.top = mHeight / 2 - 50;
            mMicArea.bottom = mHeight / 2 + 50;
        }
    }
}
