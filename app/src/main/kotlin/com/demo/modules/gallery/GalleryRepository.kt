package com.demo.modules.gallery

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

/**
 * 相册仓库，支持查询类型：
 * - 图片 ✅
 * - 视频 ❌
 */
class GalleryRepository {

    /**
     * 查询所有图片
     */
    suspend fun queryAllImages(application: Application?) =
        withContext(Dispatchers.IO) {
            application ?: return@withContext emptyList<CoffeeGalleryImage>()
            val images = mutableListOf<CoffeeGalleryImage>()
            application.contentResolver.query(
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
                    //构造图片信息
                    val image = CoffeeGalleryImage(
                        id,
                        CoffeeGalleryImage.Type.IMAGE,
                        cursor.getInt(bucketIdColumn),
                        cursor.getString(bucketNameColumn),
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    )
                    images += image
                }
            }
            images
        }

    companion object {
        /**
         * 由[queryAllImages]获取的所有图片解析出图片目录列表
         */
        fun List<CoffeeGalleryImage>.toBucketList() =
            this.groupBy { it.bucketId }
                .map {
                    val first = it.value.first()
                    CoffeeGalleryBucket(
                        bucketId = it.key,
                        bucketName = first.bucketName,
                        coverUri = first.contentUri,
                        count = it.value.count()
                    )
                }
    }
}

/**
 * 图片信息
 */
@Parcelize
data class CoffeeGalleryImage(
    val id: Long, //图片id
    val type: Type, //资源类型
    val bucketId: Int, //目录id
    val bucketName: String, //目录名字
    val contentUri: Uri, //图片uri
) : Parcelable {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoffeeGalleryImage>() {
            override fun areItemsTheSame(oldItem: CoffeeGalleryImage, newItem: CoffeeGalleryImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CoffeeGalleryImage,
                newItem: CoffeeGalleryImage,
            ) = oldItem == newItem
        }
        val CAPTURE_PLACE_HOLDER = CoffeeGalleryImage(0, Type.CAMERA, 0, "", Uri.EMPTY)
    }
    /**
     * 资源类型
     */
    enum class Type {
        IMAGE, //图片
        VIDEO, //视频（现在不支持查询视频，所以暂时没用）
        CAMERA //相机预览
    }
}

/**
 * 图片目录
 */
data class CoffeeGalleryBucket(
    val bucketId: Int = RECENT_BUCKET_ID, //分类id
    val bucketName: String = "", //分类名
    val coverUri: Uri = Uri.EMPTY, //封面uri，即第一张照片uri
    val count: Int = 0, //照片数量
) {
    companion object {
        const val RECENT_BUCKET_ID = 0 //最近项目目录id
    }
}
