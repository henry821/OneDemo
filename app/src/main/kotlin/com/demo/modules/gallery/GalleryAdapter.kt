package com.demo.modules.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.demo.one.databinding.GalleryCameraRecycleItemBinding
import com.demo.one.databinding.GalleryImageRecycleItemBinding
import com.demo.utils.screenWidth
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder


class GalleryAdapter(
    private val column: Int,
    private val captureAction: () -> Unit,
    private val itemClickAction: (CoffeeGalleryImage) -> Unit,
) : ListAdapter<CoffeeGalleryImage, AbsGalleryViewHolder<*>>(CoffeeGalleryImage.diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsGalleryViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CAMERA -> {
                val binding = GalleryCameraRecycleItemBinding.inflate(inflater, parent, false)
                CameraViewHolder(binding, captureAction)
            }

            else -> {
                val binding = GalleryImageRecycleItemBinding.inflate(inflater, parent, false)
                val itemSize = screenWidth / column
                ImageViewHolder(binding, itemSize, itemClickAction)
            }
        }

    }

    override fun onBindViewHolder(holder: AbsGalleryViewHolder<*>, position: Int) =
        holder.bind(getItem(position))

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            CoffeeGalleryImage.Type.CAMERA -> TYPE_CAMERA
            else -> TYPE_IMAGE
        }
    }

    companion object {
        private const val TYPE_CAMERA = 0
        private const val TYPE_IMAGE = 1
    }
}

abstract class AbsGalleryViewHolder<VB : ViewBinding>(binding: VB) : ViewHolder(binding.root) {
    abstract fun bind(data: CoffeeGalleryImage)
}

class CameraViewHolder(
    private val binding: GalleryCameraRecycleItemBinding,
    private val captureAction: () -> Unit,
) : AbsGalleryViewHolder<GalleryCameraRecycleItemBinding>(binding) {
    override fun bind(data: CoffeeGalleryImage) {
        binding.root.setOnClickListener { captureAction() }
    }
}

class ImageViewHolder(
    private val binding: GalleryImageRecycleItemBinding,
    private val itemSize: Int,
    private val itemClickAction: (CoffeeGalleryImage) -> Unit,
) : AbsGalleryViewHolder<GalleryImageRecycleItemBinding>(binding) {
    override fun bind(data: CoffeeGalleryImage) {
        binding.root.setOnClickListener { itemClickAction(data) }
        val request = ImageRequestBuilder.newBuilderWithSource(data.contentUri)
            .setResizeOptions(ResizeOptions(itemSize, itemSize))
            .build()
        val controller =
            Fresco.newDraweeControllerBuilder().setOldController(binding.root.controller)
                .setImageRequest(request)
                .build()
        binding.root.controller = controller
//        binding.root.setImageURI(data.contentUri)
    }
}