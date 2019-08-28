package com.baselibrary.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.baselibrary.R;
import com.baselibrary.beans.TurnPlateViewItemBean;
import com.baselibrary.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 转盘
 * Author wanghengwei
 * Date   2019/8/28 9:55
 */
public class TurnPlateView extends View {

    private int mPadding;
    /**
     * 转盘背景
     */
    private Bitmap mBgBitmap;
    /**
     * 开始按钮
     */
    private Bitmap mStartBtnBitmap;

    private Rect mBackgroundRect;
    private Rect mStartBtnRect;

    private RectF mRecf;
    private Paint mArcPaint;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;
    /**
     * 转盘边框宽度
     */
    private int mBorder;

    private int mOffset;
    private ValueAnimator mAnimator;

    private List<TurnPlateViewItemBean> mDataList;

    public TurnPlateView(Context context) {
        this(context, null);
    }

    public TurnPlateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurnPlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPadding = DensityUtil.dip2px(context, 20);
        mBorder = DensityUtil.dip2px(context, 29);

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_turn_plate_view);
        mStartBtnBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_turn_plate_view_start);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);

        mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String color = i % 2 == 0 ? "#00ff00" : "#ff0000";
            mDataList.add(new TurnPlateViewItemBean(color, String.valueOf(i)));
        }

        mAnimator = ValueAnimator.ofInt(0, 360);
        mAnimator.setDuration(1500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                width = mBgBitmap.getWidth();
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                height = mBgBitmap.getHeight();
                break;
        }

        //计算转盘背景位置
        int bgRecLeft = 0;
        int bgRecTop = 0;
        int bgRecRight = 0;
        int bgRecBottom = 0;
        if (mBackgroundRect == null) {
            bgRecLeft = width < mBgBitmap.getWidth() ? 0 : (width - mBgBitmap.getWidth()) / 2;
            bgRecTop = height < mBgBitmap.getHeight() ? 0 : (height - mBgBitmap.getHeight()) / 2;
            bgRecRight = width < mBgBitmap.getWidth() ? width : bgRecLeft + mBgBitmap.getWidth();
            bgRecBottom = height < mBgBitmap.getHeight() ? height : bgRecTop + mBgBitmap.getHeight();
            mBackgroundRect = new Rect(bgRecLeft, bgRecTop, bgRecRight, bgRecBottom);
        }

        //计算开始按钮位置
        if (mStartBtnRect == null) {
            int recLeft = mBackgroundRect.width() < mStartBtnBitmap.getWidth() ? 0 : mBackgroundRect.left + (mBackgroundRect.width() - mStartBtnBitmap.getWidth()) / 2;
            int recTop = mBackgroundRect.height() < mStartBtnBitmap.getHeight() ? 0 : mBackgroundRect.top + (mBackgroundRect.height() - mStartBtnBitmap.getHeight()) / 2;
            int recRight = mBackgroundRect.width() < mStartBtnBitmap.getWidth() ? mBackgroundRect.width() : recLeft + mStartBtnBitmap.getWidth();
            int recBottom = mBackgroundRect.width() < mStartBtnBitmap.getHeight() ? mBackgroundRect.height() : recTop + mStartBtnBitmap.getHeight();
            mStartBtnRect = new Rect(recLeft, recTop, recRight, recBottom);
        }

        //计算圆弧绘制区域
        if (mRecf == null) {
            mRecf = new RectF(bgRecLeft + mBorder, bgRecTop + mBorder, bgRecRight - mBorder, bgRecBottom - mBorder);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBgBitmap, null, mBackgroundRect, null);

        int itemDegree = 360 / mDataList.size();
        for (int i = 0; i < mDataList.size(); i++) {
            mArcPaint.setColor(Color.parseColor(mDataList.get(i).getColor()));
            canvas.drawArc(mRecf, mOffset + i * itemDegree, itemDegree, true, mArcPaint);
            canvas.drawText(mDataList.get(i).getDesc()
                    , mOffset + i * 10 + 150, mOffset + i * 10 + 150
                    , mTextPaint);
        }

        canvas.drawBitmap(mStartBtnBitmap, null, mStartBtnRect, null);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBgBitmap.recycle();
        mStartBtnBitmap.recycle();
    }

    public void startAnim() {
        mAnimator.start();
    }

}
