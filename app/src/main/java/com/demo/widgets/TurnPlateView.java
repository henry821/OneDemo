package com.demo.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;

import com.demo.beans.TurnPlateViewItemBean;
import com.demo.one.R;
import com.demo.one.base.utils.LogUtil;
import com.demo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description 转盘
 * Author wanghengwei
 * Date   2019/8/28 9:55
 */
public class TurnPlateView extends View {
    private static final float CHANGE_SPEED_DISTANCE = 100;
    private int mRadius;
    /**
     * 转盘背景Bitmap
     */
    private Bitmap mBgBitmap;
    /**
     * 开始按钮Bitmap
     */
    private Bitmap mStartBtnBitmap;
    /**
     * 绘制背景图像所需的Rect
     */
    private Rect mBgRect;
    /**
     * 绘制开始按钮所需的Rect
     */
    private Rect mStartBtnRect;
    /**
     * 绘制圆弧所需的Rect
     */
    private RectF mArcRec;
    /**
     * 绘制文字所需的Path
     */
    private Path mTextPath;
    /**
     * 绘制圆弧的画笔
     */
    private Paint mArcPaint;
    /**
     * 文字画笔
     */
    private Paint mTextPaint;
    /**
     * 转盘块偏移量
     */
    private float mOffset;
    /**
     * 数据列表
     */
    private List<TurnPlateViewItemBean> mDataList;
    /**
     * 根据id寻找对应的转盘条目
     */
    private SparseArray<TurnPlateViewItemBean> mDataMap;
    /**
     * 每个转盘条目的度数
     */
    private int mItemAngle;
    /**
     * 初始偏移值
     */
    private int mDefaultOffset;
    private Disposable mTurnDisposable;
    /**
     * 需要停到的转盘条目的索引
     */
    private int mFinalIndex;
    /**
     * 转盘停止位置
     */
    private float mStopAngle;

    public TurnPlateView(Context context) {
        this(context, null);
    }

    public TurnPlateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurnPlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = DensityUtil.dip2px(context, 89);

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_turn_plate_view);
        mStartBtnBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_turn_plate_view_start);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

        initData();

        mItemAngle = 360 / mDataList.size();
        mDefaultOffset = -90 - mItemAngle / 2;
//        mDefaultOffset = -90 - mItemAngle;
//        mDefaultOffset = 0;

        for (int i = 0; i < mDataList.size(); i++) {
            TurnPlateViewItemBean bean = mDataList.get(i);
            bean.setStartAngle(i * mItemAngle);
            bean.setEndAngle((i + 1) * mItemAngle);
            LogUtil.w("第" + i + "项 : startAngle = " + bean.getStartAngle() + " endAngle = " + bean.getEndAngle());
        }

        mTextPath = new Path();

        mFinalIndex = 5;
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

        //控件中心点
        int centerX = width / 2;
        int centerY = height / 2;

        //计算转盘背景位置
        int bgRecLeft;
        int bgRecTop;
        int bgRecRight;
        int bgRecBottom;
        if (mBgRect == null) {
            bgRecLeft = width < mBgBitmap.getWidth() ? 0 : (width - mBgBitmap.getWidth()) / 2;
            bgRecTop = height < mBgBitmap.getHeight() ? 0 : (height - mBgBitmap.getHeight()) / 2;
            bgRecRight = width < mBgBitmap.getWidth() ? width : bgRecLeft + mBgBitmap.getWidth();
            bgRecBottom = height < mBgBitmap.getHeight() ? height : bgRecTop + mBgBitmap.getHeight();
            mBgRect = new Rect(bgRecLeft, bgRecTop, bgRecRight, bgRecBottom);
        }

        //计算开始按钮位置
        if (mStartBtnRect == null) {
            int recLeft = mBgRect.width() < mStartBtnBitmap.getWidth() ? 0 : mBgRect.left + (mBgRect.width() - mStartBtnBitmap.getWidth()) / 2;
            int recTop = mBgRect.height() < mStartBtnBitmap.getHeight() ? 0 : mBgRect.top + (mBgRect.height() - mStartBtnBitmap.getHeight()) / 2;
            int recRight = mBgRect.width() < mStartBtnBitmap.getWidth() ? mBgRect.width() : recLeft + mStartBtnBitmap.getWidth();
            int recBottom = mBgRect.width() < mStartBtnBitmap.getHeight() ? mBgRect.height() : recTop + mStartBtnBitmap.getHeight();
            mStartBtnRect = new Rect(recLeft, recTop, recRight, recBottom);
        }

        //计算圆弧绘制区域
        if (mArcRec == null) {
            mArcRec = new RectF(centerX - mRadius, centerY - mRadius, centerX + mRadius, centerY + mRadius);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBgBitmap, null, mBgRect, null);

        for (int i = 0; i < mDataList.size(); i++) {
            float startAngle = mOffset + mDefaultOffset + i * mItemAngle;
            mArcPaint.setColor(Color.parseColor(mDataList.get(i).getColor()));
            canvas.drawArc(mArcRec, startAngle, mItemAngle, true, mArcPaint);

            mTextPath.reset();
            mTextPath.addArc(mArcRec, startAngle, mItemAngle);
            float textWidth = mTextPaint.measureText(mDataList.get(i).getDesc());
            //水平偏移
            float hOffset = (float) (mArcRec.width() * Math.PI / mDataList.size() / 2 - textWidth / 2);
            //竖直偏移
            float vOffset = mArcRec.width() / 2 / 4;
            canvas.drawTextOnPath(mDataList.get(i).getDesc(), mTextPath, hOffset, vOffset, mTextPaint);
        }

        canvas.drawBitmap(mStartBtnBitmap, null, mStartBtnRect, null);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBgBitmap.recycle();
        mStartBtnBitmap.recycle();
        if (mTurnDisposable != null) {
            mTurnDisposable.dispose();
            mTurnDisposable = null;
        }
    }

    public void startAnim() {
        if (mTurnDisposable != null) {
            mTurnDisposable.dispose();
        }
        mTurnDisposable = Flowable.interval(10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        float temp = mOffset;
                        if (aLong < CHANGE_SPEED_DISTANCE) {
                            //前100弧度加速，最终加速至0.2弧度/ms
                            mOffset = (float) Math.pow(aLong / CHANGE_SPEED_DISTANCE, 2) * CHANGE_SPEED_DISTANCE;
                        } else {
                            //旋转3圈后开始寻找终点并停到指定位置
                            if (mOffset >= 1080) {
                                //根据结果计算出停止位置，排除转盘项间中线位置
                                if (mOffset == 1080) {
                                    TurnPlateViewItemBean bean = mDataMap.get(mFinalIndex);
                                    mStopAngle = getRandomDegree(bean.getStartAngle() + 2, bean.getEndAngle() - 2);
                                    LogUtil.e("停止位置 : " + mStopAngle);
                                }
                                //达到停止位置后停止转动
                                if (mOffset - 1080 == mStopAngle) {
                                    mTurnDisposable.dispose();
                                    return;
                                }
                            }
                            //保持0.2弧度/ms的速度匀速转动
                            mOffset = 2 * aLong - CHANGE_SPEED_DISTANCE;
                        }
                        LogUtil.e(aLong + " " + mOffset + " 当前速度 : " + (mOffset - temp) / 10 + "弧度/ms");
                        invalidate();
                    }
                });
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mDataMap = new SparseArray<>();
        for (int i = 0; i < 8; i++) {
            String color = i % 2 == 0 ? "#00ff00" : "#ff0000";
            TurnPlateViewItemBean bean = new TurnPlateViewItemBean(i, color, "第" + i + "项");
            mDataList.add(bean);
            mDataMap.append(i, bean);
        }
    }

    /**
     * 获取随机弧度
     *
     * @return 随机弧度，根据转盘运行速度计算，只能是双数
     */
    private float getRandomDegree(float startDegree, float endDegree) {
        LogUtil.i(startDegree + " " + endDegree);
        double random = Math.random() * (endDegree - startDegree) + startDegree;
        float result = (float) (360 - Math.rint(random) + mItemAngle / 2);
        return result % 2 == 0 ? result : result + 1;
    }

}
