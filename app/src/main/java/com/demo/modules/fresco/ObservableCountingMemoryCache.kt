package com.demo.modules.fresco

import android.graphics.Bitmap
import com.facebook.cache.common.CacheKey
import com.facebook.common.internal.Predicate
import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.references.CloseableReference
import com.facebook.imagepipeline.cache.CountingMemoryCache
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.image.CloseableImage

class ObservableCountingMemoryCache(private val delegate: CountingMemoryCache<CacheKey, CloseableImage>) :
    CountingMemoryCache<CacheKey, CloseableImage> {
    override fun cache(
        key: CacheKey?,
        valueRef: CloseableReference<CloseableImage>?,
        observer: CountingMemoryCache.EntryStateObserver<CacheKey>?,
    ) = delegate.cache(key, valueRef, observer)

    override fun cache(
        key: CacheKey?,
        value: CloseableReference<CloseableImage>,
    ) = delegate.cache(key, value)

    override fun contains(key: CacheKey?) = delegate.contains(key)
    override fun contains(predicate: Predicate<CacheKey>) = delegate.contains(predicate)
    override fun get(key: CacheKey?) = delegate[key]
    override fun inspect(key: CacheKey?) = delegate.inspect(key)
    override fun probe(key: CacheKey?) = delegate.probe(key)
    override fun removeAll(predicate: Predicate<CacheKey>) = delegate.removeAll(predicate)
    override fun trim(trimType: MemoryTrimType?) = delegate.trim(trimType)
    override fun maybeEvictEntries() = delegate.maybeEvictEntries()
    override fun getInUseSizeInBytes() = delegate.inUseSizeInBytes
    override fun getEvictionQueueCount() = delegate.evictionQueueCount
    override fun getEvictionQueueSizeInBytes() = delegate.evictionQueueSizeInBytes
    override fun clear() = delegate.clear()
    override fun getMemoryCacheParams(): MemoryCacheParams = delegate.memoryCacheParams
    override fun getCachedEntries() = delegate.cachedEntries
    override fun getOtherEntries(): MutableMap<Bitmap, Any>? = delegate.otherEntries

    override val count: Int
        get() = delegate.count
    override val sizeInBytes: Int
        get() = delegate.sizeInBytes
    override val debugData: String?
        get() = delegate.debugData

    override fun reuse(key: CacheKey?) = delegate.reuse(key)

}