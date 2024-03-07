package com.demo.widgets.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * ViewPagerAdapter基类
 * Created by hengwei on 2021/7/2.
 */
open class BasePagerAdapter<T>(val dataList: List<T>, private val listener: Listener<T>) :
    PagerAdapter() {

    //保存可复用的View
    private val availableViewPool = mutableListOf<View>()

    override fun getCount(): Int {
        return dataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = if (availableViewPool.isEmpty()) {
            listener.onCreateView(container.context)
        } else {
            availableViewPool.removeAt(0)
        }
        listener.onBindView(view, dataList[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
        availableViewPool.add(`object`)

    }

    interface Listener<T> {
        fun onCreateView(context: Context): View
        fun onBindView(view: View, data: T)
    }
}