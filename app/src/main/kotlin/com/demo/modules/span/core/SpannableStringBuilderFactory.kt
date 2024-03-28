package com.demo.modules.span.core

import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.ArrayMap
import android.util.Log
import android.widget.TextView
import androidx.core.text.BidiFormatter
import com.demo.one.BuildConfig
import java.util.LinkedList

/**
 * 简化Span拼接工具，有以下特点：
 * 1. 提前注册[Processor]，运行时根据数据源动态选择[Processor]生成Span并拼接到一起
 * 2. 支持RTL布局下拼接后字符串的正确显示
 * 3. 可以提供统一的缓存池，缓存生成的Span
 *
 * 使用方式：
 * ```
 * 1. 提前注册Span
 *
 * val builder = SpannableStringBuilderFactory.Builder<LiveMsgPrefixInfo>()
 *                   .append(PkProcessor())
 *                   .append(……)
 *                   .overlay(ClickProcessor())
 *                   .overlay(……)
 * val factory = builder.build()
 *
 * 2. 根据输入数据拼接字符串并赋值给TextView
 *
 * factory.load(liveMsgPrefixInfo)              // 绑定数据源
 *        .cacheable(true)                      // [可选]是否可缓存
 *        .setCachePool(cachePool)              // [可选]外部设置缓存池，如果不设置的话框架提供默认缓存池
 *        .isRtl(RtlUtils.isRtl(getContext()))  // [可选]是否rtl布局
 *        .into(textView);                      // 结果赋值给TextView
 * ```
 */
class SpannableStringBuilderFactory<T : Any> private constructor() {

    constructor(builder: Builder<T>) : this() {
        mNodeProcessors = LinkedList(builder.nodeProcessors)
        mOverlayProcessors = LinkedList(builder.overlayProcessors)
    }

    private lateinit var mTarget: TextView
    private lateinit var mData: T

    private var mCacheable = false
        set(value) {
            if (value && mCachePool == null) mCachePool = CachePool()
            field = value
        }
    private var mCachePool: CachePool? = null
        get() = if (mCacheable) field else null

    private var mIsRtl = false

    private lateinit var mNodeProcessors: LinkedList<Processor<T>>
    private lateinit var mOverlayProcessors: LinkedList<OverlayProcessor<T>>
    private val commands = mutableListOf<ProcessCommand>()

    fun load(data: T) = apply { mData = data }
    fun isRtl(isRtl: Boolean) = apply { mIsRtl = isRtl }
    fun cacheable(cacheable: Boolean) = apply { mCacheable = cacheable }
    fun setCachePool(pool: CachePool) = apply { mCachePool = pool }

    fun into(t: TextView) {
        "into Start: data.hashCode = ${mData.hashCode()}".logI()
        commands.clear()
        mTarget = t
        val sb = StringBuilder()

        for (it in mNodeProcessors) {
            if (!it.handled(mData)) continue
            val str =
                BidiFormatter.getInstance().unicodeWrap(it.content(mData, mIsRtl, mTarget)) //关键一句
            val startIndex = sb.length
            val endIndex = startIndex + str.length
            var span = mCachePool?.getCachedSpan(mData, it, mIsRtl)
            if (span == null) {
                span = it.provideSpan(mData, mIsRtl, mTarget)
                //容错逻辑：如果是图片Span但是Span没有生成，不能把占位符展示出来，所以直接跳过循环不予展示
                if (span == null && it is ImageProcessor) continue
                mCachePool?.putCachedSpan(mData, it, span, mIsRtl)
            }
            commands.add(ProcessCommand(startIndex, endIndex, span))
            sb.append(str).append((if (it.appendSpace()) " " else ""))
        }

        for (it in mOverlayProcessors) {
            if (!it.handled(mData)) continue
            var span = mCachePool?.getCachedSpan(mData, it, mIsRtl)
            val range = it.range(mData, sb.toString())
            if (span == null) {
                span = it.provideSpan(mData, mIsRtl, mTarget)
                mCachePool?.putCachedSpan(mData, it, span, mIsRtl)
            }
            commands.add(ProcessCommand(range.first, range.second, span))
        }

        "字符串拼接完成：$sb".logI()

        val builder = SpannableStringBuilder(sb)
        for (cmd in commands) {
            if (cmd.span == null) continue
            builder.setSpan(cmd.span, cmd.startIndex, cmd.endIndex, DEFAULT_FLAG)
        }
        "into End: data.hashCode = ${mData.hashCode()}, cacheSize = ${mCachePool?.size()}".logI()
        t.text = builder
    }

    class Builder<T : Any> {

        private val _nodeProcessors = mutableListOf<Processor<T>>()
        val nodeProcessors: List<Processor<T>> get() = _nodeProcessors

        private val _overlayProcessors by lazy { mutableListOf<OverlayProcessor<T>>() }
        val overlayProcessors: List<OverlayProcessor<T>> get() = _overlayProcessors

        fun append(processor: Processor<T>) = apply { _nodeProcessors.add(processor) }
        fun overlay(processor: OverlayProcessor<T>) = apply { _overlayProcessors.add(processor) }

        fun build(): SpannableStringBuilderFactory<T> {
            require(_nodeProcessors.isNotEmpty() or _overlayProcessors.isNotEmpty()) {
                "At Least One Processor"
            }
            return SpannableStringBuilderFactory(this)
        }
    }

    companion object {
        private const val DEFAULT_FLAG = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    }

    /**
     * 简易缓存池，用来缓存生成过的Span
     *
     * 缓存策略：用[ArrayMap]存储
     *
     * * key的生成方式：{用户提供的tag} _ {processor类名} _ {rtl布局}
     *                     |
     *           未提供tag则默认为数据源的hashcode
     * * 回收逻辑：默认阈值200，如果超过阈值则在Looper空闲时移除一半数据
     */
    class CachePool {
        private val mPool = ArrayMap<String, Any>()
        private var mThreshold = THRESHOLD
        private var mRecycling = false

        fun <T> putCachedSpan(
            data: T,
            processor: Processor<T>,
            span: Any?,
            isRtl: Boolean,
        ) {
            if (span == null) return
            val tag = combineTag(data, processor, isRtl)
            mPool[tag] = span

            if (mPool.size <= mThreshold) return
            if (mRecycling) return
            mRecycling = true
            Looper.myQueue().addIdleHandler {
                "缓存池[${hashCode()}]容量${mPool.size} > 阈值$mThreshold, 开始回收".logI()
                for (i in 0..mThreshold / 2) mPool.removeAt(i)
                "回收完成，缓存池容量：${mPool.size}".logI()
                mRecycling = false
                false
            }
        }

        fun <T> getCachedSpan(
            data: T,
            processor: Processor<T>,
            isRtl: Boolean,
        ): Any? {
            val tag = combineTag(data, processor, isRtl)
            val result = mPool[tag]
            "从缓存池[${hashCode()}]获取缓存: tag=$tag, 是否取到：$result".logI()
            return result
        }

        fun size() = mPool.size

        private fun <T> combineTag(
            data: T,
            processor: Processor<T>,
            isRtl: Boolean,
        ): String {
            return "${
                processor.provideTag(data).ifBlank { data.hashCode() }
            }_${processor.javaClass.simpleName}_${if (isRtl) 1 else 0}"
        }

        companion object {
            /**
             * 默认阈值
             */
            private const val THRESHOLD = 200
        }
    }

}

private data class ProcessCommand(
    val startIndex: Int,
    val endIndex: Int,
    val span: Any?,
)

private fun String.logI() {
    if (BuildConfig.DEBUG) Log.i("SSBF", this)
}