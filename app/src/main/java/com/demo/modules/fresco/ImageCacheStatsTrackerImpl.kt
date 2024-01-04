package com.demo.modules.fresco

import android.util.Log
import com.facebook.cache.common.CacheKey
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker
import com.facebook.imagepipeline.cache.MemoryCache

class ImageCacheStatsTrackerImpl : ImageCacheStatsTracker {

    override fun onBitmapCacheHit(cacheKey: CacheKey) {
        cacheKeyLog("bitmap缓存命中", cacheKey)
    }

    override fun onBitmapCacheMiss(cacheKey: CacheKey) {
        cacheKeyLog("bitmap缓存未命中", cacheKey)
    }

    override fun onBitmapCachePut(cacheKey: CacheKey) {
        cacheKeyLog("放入bitmap缓存", cacheKey)
    }

    override fun onDiskCacheGetFail(cacheKey: CacheKey) {
        cacheKeyLog("磁盘缓存获取失败", cacheKey)
    }

    override fun onDiskCacheHit(cacheKey: CacheKey) {
        cacheKeyLog("磁盘缓存命中", cacheKey)
    }

    override fun onDiskCacheMiss(cacheKey: CacheKey) {
        cacheKeyLog("磁盘缓存未命中", cacheKey)
    }

    override fun onDiskCachePut(cacheKey: CacheKey) {
        cacheKeyLog("放入磁盘缓存", cacheKey)
    }

    override fun onMemoryCacheHit(cacheKey: CacheKey) {
        cacheKeyLog("未解码缓存命中", cacheKey)
    }

    override fun onMemoryCacheMiss(cacheKey: CacheKey) {
        cacheKeyLog("未解码缓存未命中", cacheKey)
    }

    override fun onMemoryCachePut(cacheKey: CacheKey) {
        cacheKeyLog("放入未解码缓存", cacheKey)
    }

    override fun onStagingAreaHit(cacheKey: CacheKey) {
        cacheKeyLog("StagingArea命中", cacheKey)
    }

    override fun onStagingAreaMiss(cacheKey: CacheKey) {
        cacheKeyLog("StagingArea未命中", cacheKey)
    }

    override fun registerBitmapMemoryCache(bitmapMemoryCache: MemoryCache<*, *>) {
        Log.i(TAG_MONITOR_FRESCO, "注册bitmap缓存：${bitmapMemoryCache.javaClass.simpleName} ")
    }

    override fun registerEncodedMemoryCache(encodedMemoryCache: MemoryCache<*, *>) {
        Log.i(TAG_MONITOR_FRESCO, "注册未解码缓存：${encodedMemoryCache.javaClass.simpleName} ")
    }

    private fun cacheKeyLog(msg: String, cacheKey: CacheKey) {
        Log.i(TAG_MONITOR_FRESCO, "[${cacheKey.javaClass.simpleName}:${cacheKey.hashCode()}]$msg")
    }
}