package com.demo.modules.span.core

import android.widget.TextView

/**
 * [SpannableStringBuilderFactory]的简化使用，如果仅希望用流式操作拼接Span的话，可以使用此工具。
 *
 * 使用方式：
 * ```
 * tvSpan.spannable()      // 开启
 *       .append(……)       // 要拼接的Span，     append和overlay的Span至少有一个
 *       .overlay(……)      // 要覆盖某区间的Span，append和overlay的Span至少有一个
 *       .isRtl(……)        // [可选]是否rtl布局
 *       .cacheable(……)    // [可选]是否支持缓存
 *       .setCachePool(……) // [可选]可以提供统一的缓存池
 *       .withSpace(……)    // [可选]是否在Span间自动插入空格
 *       .commit()         // 结束
 * ```
 *
 * Api类似[SpannableStringBuilderFactory]，可以较低成本上手
 */
class SpannableStringBuilderFactoryWrapper(
    private val textView: TextView,
    private val builder: SpannableStringBuilderFactory.Builder<Any>,
) {
    private var factory: SpannableStringBuilderFactory<Any>? = null
    private var mAppendSpace = false

    /**
     * 添加Span
     * @param contentCreator 根据[isRtl]生成对应的字符串内容（或占位符）
     * @param spanCreator 根据[isRtl]生成对应的Span, 可为null，即纯文本
     */
    fun append(
        contentCreator: (isRtl: Boolean) -> String,
        spanCreator: (isRtl: Boolean) -> Any?,
    ): SpannableStringBuilderFactoryWrapper {
        val processor = object : Processor<Any> {
            override fun handled(data: Any) = true
            override fun content(data: Any, isRtl: Boolean, target: TextView) =
                contentCreator(isRtl)

            override fun provideSpan(data: Any, isRtl: Boolean, target: TextView) =
                spanCreator(isRtl)

            override fun appendSpace() = mAppendSpace
        }
        builder.append(processor)
        return this
    }

    /**
     * 覆盖区间的Span
     * @param positionCreator 根据全文本[wholeString]生成对应的区间
     * @param spanCreator 根据[isRtl]生成对应的Span, 可为null，即纯文本
     */
    fun overlay(
        positionCreator: (wholeString: String) -> Pair<Int, Int>,
        spanCreator: (isRtl: Boolean) -> Any?,
    ): SpannableStringBuilderFactoryWrapper {
        val processor = object : OverlayProcessor<Any> {
            override fun handled(data: Any) = true
            override fun provideSpan(data: Any, isRtl: Boolean, target: TextView) =
                spanCreator(isRtl)

            override fun range(data: Any, wholeString: String) = positionCreator(wholeString)
            override fun appendSpace() = mAppendSpace
        }
        builder.overlay(processor)
        return this
    }

    fun withSpace(flag: Boolean) {
        mAppendSpace = flag
    }

    fun isRtl(isRtl: Boolean): SpannableStringBuilderFactoryWrapper {
        if (factory == null) factory = builder.build()
        factory?.isRtl(isRtl)
        return this
    }

    fun cacheable(cacheable: Boolean): SpannableStringBuilderFactoryWrapper {
        if (factory == null) factory = builder.build()
        factory?.cacheable(cacheable)
        return this
    }

    fun setCachePool(pool: SpannableStringBuilderFactory.CachePool): SpannableStringBuilderFactoryWrapper {
        if (factory == null) factory = builder.build()
        factory?.setCachePool(pool)
        return this
    }

    fun commit() {
        if (factory == null) factory = builder.build()
        factory?.load("")?.into(textView)
    }


}

fun TextView.spannable(): SpannableStringBuilderFactoryWrapper =
    SpannableStringBuilderFactoryWrapper(this, SpannableStringBuilderFactory.Builder())