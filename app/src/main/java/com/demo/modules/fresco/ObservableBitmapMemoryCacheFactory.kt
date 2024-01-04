package com.demo.modules.fresco

import com.facebook.cache.common.CacheKey
import com.facebook.common.internal.Supplier
import com.facebook.common.memory.MemoryTrimmableRegistry
import com.facebook.imagepipeline.cache.CountingLruBitmapMemoryCacheFactory
import com.facebook.imagepipeline.cache.CountingMemoryCache
import com.facebook.imagepipeline.cache.MemoryCache
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.image.CloseableImage

class ObservableBitmapMemoryCacheFactory : CountingLruBitmapMemoryCacheFactory() {
    override fun create(
        bitmapMemoryCacheParamsSupplier: Supplier<MemoryCacheParams>,
        memoryTrimmableRegistry: MemoryTrimmableRegistry,
        trimStrategy: MemoryCache.CacheTrimStrategy,
        storeEntrySize: Boolean,
        ignoreSizeMismatch: Boolean,
        observer: CountingMemoryCache.EntryStateObserver<CacheKey>?,
    ): CountingMemoryCache<CacheKey, CloseableImage> {
        return ObservableCountingMemoryCache(
            super.create(
                bitmapMemoryCacheParamsSupplier,
                memoryTrimmableRegistry,
                trimStrategy,
                storeEntrySize,
                ignoreSizeMismatch,
                observer
            )
        )
    }
}