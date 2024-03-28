package com.demo.modules.span.core

import android.widget.TextView

/**
 * 根据输入数据[T]生成span的处理器
 */
interface Processor<in T> {
    /**
     * 由[data]判断是否满足生成Span的条件
     */
    fun handled(data: T): Boolean

    /**
     * 根据[data]和设备布局方向[isRtl]生成显示(或占位)的文本
     */
    fun content(data: T, isRtl: Boolean, target: TextView): String

    /**
     * 根据输入数据[T]和设备布局方向[isRtl]生成Span
     * 可空,即纯文本
     */
    fun provideSpan(data: T, isRtl: Boolean, target: TextView): Any?

    /**
     * 是否在Span后插入空格,默认插入
     */
    fun appendSpace(): Boolean = true

    /**
     * 当前Span的标识，默认为输入数据[data]的hashcode
     * - 注意：如果Span和动态因素（例如用户等级）有关，则一定要把动态因素拼接到tag里
     */
    fun provideTag(data: T): String = data.hashCode().toString()
}

/**
 * 生成覆盖模式Span（不需要占位content）的处理器，例如给全部文本设置点击事件
 */
interface OverlayProcessor<T> : Processor<T> {
    override fun content(data: T, isRtl: Boolean, target: TextView) = ""

    /**
     * 根据输入数据[data]和拼接后的文本[wholeString]返回设置的索引对
     */
    fun range(data: T, wholeString: String): Pair<Int, Int>
}

/**
 * 生成图片Span可以继承此类,简化了[content]步骤
 */
interface ImageProcessor<T> : Processor<T> {
    override fun content(data: T, isRtl: Boolean, target: TextView) = "N"
}
