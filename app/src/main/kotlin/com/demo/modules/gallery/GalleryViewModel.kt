package com.demo.modules.gallery

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 相册ViewModel
 */
class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * 所有图片
     */
    private var allImages = listOf<CoffeeGalleryImage>()

    /**
     * 相册分类集合 <[CoffeeGalleryBucket.bucketId],[CoffeeGalleryBucket]>
     */
    private val bucketMap = mutableMapOf<Int, CoffeeGalleryBucket>()

    /**
     * 相册分类列表
     */
    private val _buckets = MutableStateFlow<List<CoffeeGalleryBucket>>(emptyList())
    val buckets = _buckets.asStateFlow()

    /**
     * 根据选中的相册分类[_buckets]过滤出对应的图片列表
     */
    val images = _buckets.map { list ->
        val selectedBucket = list.firstOrNull { it.selected }
        if (selectedBucket?.bucketId == DEFAULT_BUCKET.bucketId) allImages
        else allImages.filter { it.bucketId == selectedBucket?.bucketId }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            queryImages()
        }
    }

    /**
     * 扫描相册图片[allImages]，同时更新相册分类[_buckets]
     */
    private suspend fun queryImages() {
        bucketMap.clear()
        withContext(Dispatchers.IO) {
            val images = mutableListOf<CoffeeGalleryImage>()
            getApplication<Application>().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                ),
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val bucketIdColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
                val bucketNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val bucketId = cursor.getInt(bucketIdColumn)

                    //构造图片信息
                    val image = CoffeeGalleryImage(
                        id,
                        bucketId,
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    )
                    images += image

                    //构造相册分类信息
                    val bucket = bucketMap.getOrElse(bucketId) { CoffeeGalleryBucket() }
                    bucketMap[bucketId] = bucket.copy(
                        bucketId = bucketId,
                        bucketName = cursor.getString(bucketNameColumn),
                        coverUri = if (bucket.coverUri == Uri.EMPTY) image.contentUri else bucket.coverUri,
                        count = bucket.count + 1,
                        selected = false
                    )
                }
            }
            allImages = images
        }
        _buckets.value =
            bucketMap.values.toMutableList()
                .also {
                    it.add(
                        0,
                        DEFAULT_BUCKET.copy(
                            coverUri = allImages.firstOrNull()?.contentUri ?: Uri.EMPTY,
                            count = allImages.size
                        )
                    )
                }.toList()
    }

    fun selectBucket(bucketId: Int) {
        _buckets.update { list -> list.map { it.copy(selected = it.bucketId == bucketId) } }
    }

    companion object {
        val DEFAULT_BUCKET = CoffeeGalleryBucket(0, "最近项目", Uri.EMPTY, 0, true)
    }
}
