package com.demo.modules.gallery

import android.app.Application
import android.net.Uri
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.DiffUtil
import com.demo.modules.gallery.GalleryRepository.Companion.toBucketList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 相册ViewModel
 */
@Keep
class GalleryViewModel(
    private val application: Application?,
    private val enableCapture: Boolean,
) : ViewModel() {

    class ViewModelFactory(private val enableCapture: Boolean) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            return modelClass.getConstructor(Application::class.java, Boolean::class.java)
                .newInstance(application, enableCapture)
        }
    }

    private val repo = GalleryRepository()

    /**
     * 所有图片
     */
    private val _allImages = MutableStateFlow<List<CoffeeGalleryImage>>(emptyList())

    /**
     * 图片目录列表
     */
    private val _buckets = _allImages.map { list ->
        val result = mutableListOf<CoffeeGalleryBucket>()
        result.add(
            CoffeeGalleryBucket(
                bucketId = CoffeeGalleryBucket.RECENT_BUCKET_ID,
                bucketName = "最近项目",
                coverUri = list.firstOrNull()?.contentUri ?: Uri.EMPTY,
                count = list.size
            )
        )
        result.addAll(list.toBucketList())
        result.toList()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * 当前选择目录id
     */
    private val _selectedBucketId = MutableStateFlow(0)

    /**
     * 页面展示的图片目录列表
     */
    val bucketsState = combine(_buckets, _selectedBucketId) { d1, d2 ->
        d1.map { CoffeeGalleryBucketUiState(it, it.bucketId == d2) }
    }

    /**
     * 根据选中的相册分类[_buckets]过滤出对应的图片列表
     */
    val imagesState = bucketsState.map { list ->
        val selectedBucket = list.firstOrNull { it.selected }?.bucket
        val result = mutableListOf<CoffeeGalleryImage>()
        if (enableCapture) result.add(CoffeeGalleryImage.CAPTURE_PLACE_HOLDER)
        result.addAll(
            if (selectedBucket?.bucketId == CoffeeGalleryBucket.RECENT_BUCKET_ID) _allImages.value
            else _allImages.value.filter { it.bucketId == selectedBucket?.bucketId }
        )
        result.toList()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            _allImages.value = repo.queryAllImages(application)
            _selectedBucketId.value = CoffeeGalleryBucket.RECENT_BUCKET_ID
        }
    }

    fun selectBucket(bucketId: Int) {
        _selectedBucketId.value = bucketId
    }

}

/**
 * 页面展示图片目录
 */
data class CoffeeGalleryBucketUiState(val bucket: CoffeeGalleryBucket, val selected: Boolean) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoffeeGalleryBucketUiState>() {
            override fun areItemsTheSame(
                oldItem: CoffeeGalleryBucketUiState,
                newItem: CoffeeGalleryBucketUiState,
            ) = oldItem.bucket.bucketId == newItem.bucket.bucketId

            override fun areContentsTheSame(
                oldItem: CoffeeGalleryBucketUiState,
                newItem: CoffeeGalleryBucketUiState,
            ) = oldItem == newItem
        }
    }
}
