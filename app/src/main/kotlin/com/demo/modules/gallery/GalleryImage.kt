/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.modules.gallery

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class CoffeeGalleryImage(
    val id: Long, //照片id
    val bucketId: Int, //所属分类id
    val contentUri: Uri, //照片uri
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoffeeGalleryImage>() {
            override fun areItemsTheSame(oldItem: CoffeeGalleryImage, newItem: CoffeeGalleryImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CoffeeGalleryImage,
                newItem: CoffeeGalleryImage,
            ) = oldItem == newItem
        }
    }
}

/**
 * 相册分类
 */
data class CoffeeGalleryBucket(
    val bucketId: Int = -1, //分类id
    val bucketName: String = "", //分类名
    val coverUri: Uri = Uri.EMPTY, //封面uri，即第一张照片uri
    val count: Int = 0, //照片数量
    val selected: Boolean = false, //是否选中
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CoffeeGalleryBucket>() {
            override fun areItemsTheSame(
                oldItem: CoffeeGalleryBucket,
                newItem: CoffeeGalleryBucket,
            ) = oldItem.bucketId == newItem.bucketId

            override fun areContentsTheSame(
                oldItem: CoffeeGalleryBucket,
                newItem: CoffeeGalleryBucket,
            ) = oldItem == newItem
        }
    }
}