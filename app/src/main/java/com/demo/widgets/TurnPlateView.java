package com.demo.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.demo.beans.TurnPlateViewItemBean;
import com.demo.one.R;
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
    /**
     * 转盘启动加速距离
     */
    private static final float SPEED_UP_DISTANCE = 100;
    /**
     * 转盘半径
     */
    private int mRadius;
    /**
     * 数据列表
     */
    private List<TurnPlateViewItemBean> mDataList;
    /**
     * 根据id寻找对应的转盘条目
     */
    private SparseArray<TurnPlateViewItemBean> mDataMap;
    /**
     * 转盘背景Bitmap
     */
    private Bitmap mBgBitmap;
    /**
     * 开始按钮Bitmap
     */
    private Bitmap mStartBtnBitmap;
    /**
     * 关闭按钮Bitmap
     */
    private Bitmap mCloseBtnBitmap;
    /**
     * 绘制背景图像所需的Rect
     */
    private Rect mBgRect;
    /**
     * 绘制开始按钮所需的Rect
     */
    private Rect mStartBtnRect;
    /**
     * 绘制关闭按钮所需的Rect
     */
    private Rect mCloseBtnRect;
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
     * 初始偏移值
     */
    private float mDefaultOffset;
    /**
     * 转盘第一块的一半弧度
     */
    private float mHalfAngleOfFirstItem;
    /**
     * 需要停到的转盘条目的索引
     */
    private int mFinalIndex;
    /**
     * 转盘停止位置
     */
    private float mStopAngle;
    /**
     * 按下事件横坐标
     */
    private float mDownX;
    /**
     * 按下事件纵坐标
     */
    private float mDownY;
    /**
     * 当前View在屏幕中的位置
     */
    private int[] mLocation = new int[2];
    /**
     * 转盘转动disposable
     */
    private Disposable mDisposable;
    /**
     * 记录是否在转动
     */
    private boolean mIsRunning;
    /**
     * 记录上一次停止的位置
     */
    private float mLastStopPosition;
    /**
     * 记录已经转过的次数
     */
    private int mStartTimes;
    /**
     * 点击事件，包括转动转盘和关闭转盘
     */
    private OnClickListener mClickListener;
    /**
     * 开始按钮边界值，为了计算点击事件
     */
    private int mStartLeftBorder;
    private int mStartTopBorder;
    private int mStartRightBorder;
    private int mStartBottomBorder;
    /**
     * 关闭按钮边界值，为了计算点击事件
     */
    private int mCloseLeftBorder;
    private int mCloseTopBorder;
    private int mCloseRightBorder;
    private int mCloseBottomBorder;


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
        mCloseBtnBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_turn_plate_view_close);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextPaint.setLetterSpacing(0.3f);
        }
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

        mDataList = new ArrayList<>();
        mDataMap = new SparseArray<>();

        mTextPath = new Path();

        mFinalIndex = 0;

    }

    public void initData(List<TurnPlateViewItemBean> list) {
        mDataMap.clear();
        mDataList.clear();
        mDataList.addAll(list);
        float totalAngle = 0;
        for (int i = 0; i < mDataList.size(); i++) {
            TurnPlateViewItemBean bean = mDataList.get(i);
            float itemAngle = Float.parseFloat(bean.getAngle());
            bean.setStartAngle(totalAngle);
            bean.setEndAngle(totalAngle + itemAngle);
            mDataMap.put(Integer.parseInt(bean.getId()), bean);
            totalAngle += itemAngle;
            if (i == 0) {
                mHalfAngleOfFirstItem = itemAngle / 2;
                mDefaultOffset = -90 - mHalfAngleOfFirstItem;
                mFinalIndex = Integer.parseInt(bean.getId());
            }
        }
        postInvalidate();
    }

    /**
     * 开始转动
     *
     * @param resultIndex 如果为-1，说明开始时不知道结果，需要转动3圈后查看是否有结果
     *                    否则说明知道结果，转动3圈后根据传入的参数展示结果
     */
    public void start(int resultIndex) {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mIsRunning = true;
        mStopAngle = getStopAngle(resultIndex);
        mDisposable = Flowable.interval(10, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        if (aLong < SPEED_UP_DISTANCE) {
                            //前100弧度加速，最终加速至0.2弧度/ms
                            mOffset = (float) Math.pow(aLong / SPEED_UP_DISTANCE, 2) * SPEED_UP_DISTANCE + mLastStopPosition;
                        } else {
                            //达到停止位置后停止转动
                            if (mOffset == mStopAngle) {
                                stop();
                                return;
                            }
                            //保持0.2弧度/ms的速度匀速转动
                            mOffset = 2 * aLong - SPEED_UP_DISTANCE + mLastStopPosition;
                        }
                        invalidate();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        stop();
                    }
                });
    }

    public void setOnClickListener(OnClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setFinalIndex(int index) {
        mFinalIndex = index;
        mStopAngle = getStopAngle(mFinalIndex);
    }

    public boolean isInitFinished() {
        return mDataList != null && !mDataList.isEmpty();
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onDestroy() {
        mIsRunning = false;
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mBgBitmap.recycle();
        mStartBtnBitmap.recycle();
        mCloseBtnBitmap.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //根据转盘bitmap宽高和父布局的MeasureSpec计算出实际的尺寸
        int width = resolveSize(mBgBitmap.getWidth(), widthMeasureSpec);
        int height = resolveSize(mBgBitmap.getHeight(), heightMeasureSpec);

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

        //计算关闭按钮位置
        if (mCloseBtnRect == null) {
            mCloseBtnRect = new Rect(width - mCloseBtnBitmap.getWidth() - DensityUtil.dip2px(getContext(), 10)
                    , DensityUtil.dip2px(getContext(), 6)
                    , width - DensityUtil.dip2px(getContext(), 10)
                    , DensityUtil.dip2px(getContext(), 6) + mCloseBtnBitmap.getHeight());
        }

        //计算圆弧绘制区域
        if (mArcRec == null) {
            mArcRec = new RectF(centerX - mRadius, centerY - mRadius, centerX + mRadius, centerY + mRadius);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        getLocationOnScreen(mLocation);

        mStartLeftBorder = mStartBtnRect.left + mLocation[0];
        mStartTopBorder = mStartBtnRect.top + mLocation[1];
        mStartRightBorder = mStartBtnRect.right + mLocation[0];
        mStartBottomBorder = mStartBtnRect.bottom + mLocation[1];

        mCloseLeftBorder = mCloseBtnRect.left + mLocation[0];
        mCloseTopBorder = mCloseBtnRect.top + mLocation[1];
        mCloseRightBorder = mCloseBtnRect.right + mLocation[0];
        mCloseBottomBorder = mCloseBtnRect.bottom + mLocation[1];

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBgBitmap, null, mBgRect, null);

        for (int i = 0; i < mDataList.size(); i++) {
            TurnPlateViewItemBean bean = mDataList.get(i);
            float startAngle = mOffset + mDefaultOffset + bean.getStartAngle();
            mArcPaint.setColor(Color.parseColor(mDataList.get(i).getColor()));
            canvas.drawArc(mArcRec, startAngle, bean.getFloatAngle(), true, mArcPaint);

            mTextPath.reset();
            mTextPath.addArc(mArcRec, startAngle, bean.getFloatAngle());
            float textWidth = mTextPaint.measureText(bean.getTitle());
            //水平偏移
            float hOffset = (float) (mArcRec.width() * Math.PI / mDataList.size() / 2 - textWidth / 2);
            //竖直偏移
            float vOffset = mArcRec.width() / 2 / 4;
            canvas.drawTextOnPath(bean.getTitle(), mTextPath, hOffset, vOffset, mTextPaint);
        }

        canvas.drawBitmap(mStartBtnBitmap, null, mStartBtnRect, null);
        canvas.drawBitmap(mCloseBtnBitmap, null, mCloseBtnRect, null);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mClickListener == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getRawX();
                float y = event.getRawY();
                if (mDownX >= mCloseLeftBorder && mDownX <= mCloseRightBorder
                        && mDownY >= mCloseTopBorder && mDownY <= mCloseBottomBorder
                        && x >= mCloseLeftBorder && x <= mCloseRightBorder
                        && y >= mCloseTopBorder && y <= mCloseBottomBorder) {
                    mClickListener.onClose();
                } else if (mDownX >= mStartLeftBorder && mDownX <= mStartRightBorder
                        && mDownY >= mStartTopBorder && mDownY <= mStartBottomBorder
                        && x >= mStartLeftBorder && x <= mStartRightBorder
                        && y >= mStartTopBorder && y <= mStartBottomBorder) {
                    mClickListener.onClickPlate();
                }
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 获取随机弧度，去掉可能停在分割线上的情况
     *
     * @return 随机弧度，根据转盘运行速度计算，只能是双数
     */
    private float getRandomDegree(int itemIndex) {
        TurnPlateViewItemBean bean = mDataMap.get(itemIndex);
        float startAngle = bean.getStartAngle() + 2;
        float endAngle = bean.getEndAngle() - 2;
        double random = Math.random() * (endAngle - startAngle) + startAngle;
        float result = (float) (360 - Math.rint(random - mHalfAngleOfFirstItem));
        return result % 2 == 0 ? result : result + 1;
    }

    /**
     * 计算最终停止的位置,排除转盘项间中线位置
     * 计算方式：根据结果计算出的随机度数+至今为止转动的总圈数+本次需要转动的3圈
     *
     * @param resultIndex 本次停止的转盘块的索引
     * @return 本次转动最终停止的位置
     */
    private float getStopAngle(int resultIndex) {
        return getRandomDegree(resultIndex == -1 ? mFinalIndex : resultIndex) + ((mStartTimes + 1) * 3 * 360);
    }

    private void stop() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        //如果在转动状态调用此方法(转动正常结束时调用此方法、转动未结束时关闭页面)时，则增加启动次数
        //如果在停止状态调用此方法(转动正常结束后关闭页面)时，则不增加启动次数
        if (mIsRunning) {
            mStartTimes++;
        }
        mIsRunning = false;
        mLastStopPosition = mStopAngle;
        //如果停止的时候当前转动角度和计算出的最终停止角度不一致(转动未结束时关闭页面)，则强制刷新一次页面，校准数据
        if (mOffset != mStopAngle) {
            mOffset = mStopAngle;
            postInvalidate();
        }
    }

    public interface OnClickListener {
        void onClose();

        void onClickPlate();
    }

}
