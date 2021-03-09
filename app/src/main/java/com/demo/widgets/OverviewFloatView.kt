package com.demo.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.demo.one.R
import com.demo.utils.ScreenUtil
import com.demo.widgets.base.DragView

/**
 * Created by hengwei on 2021/3/9.
 */
class OverviewFloatView : DragView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.layout_overview, this)

        findViewById<TextView>(R.id.tv_width).text = context.getString(R.string.screen_width,
                ScreenUtil.getScreenWidth(context),
                ScreenUtil.convertPxToDp(context, ScreenUtil.getScreenWidth(context)))
        findViewById<TextView>(R.id.tv_height).text = context.getString(R.string.screen_height,
                ScreenUtil.getScreenHeight(context),
                ScreenUtil.convertPxToDp(context, ScreenUtil.getScreenHeight(context)))
        findViewById<TextView>(R.id.tv_density).text = context.getString(R.string.screen_density,
                ScreenUtil.getScreenDensity(context))
    }

}