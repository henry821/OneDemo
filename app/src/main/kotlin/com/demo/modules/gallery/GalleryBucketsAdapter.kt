package com.demo.modules.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.databinding.GalleryBucketsRecycleItemBinding

/**
 * 图片目录Adapter
 */
class GalleryBucketsAdapter(private val itemClickAction: (CoffeeGalleryBucketUiState) -> Unit) :
    ListAdapter<CoffeeGalleryBucketUiState, BucketViewHolder>(CoffeeGalleryBucketUiState.diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryBucketsRecycleItemBinding.inflate(inflater, parent, false)
        return BucketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {
        holder.itemView.setOnClickListener { itemClickAction.invoke(getItem(position)) }
        holder.bind(getItem(position))
    }

}

class BucketViewHolder(private val binding: GalleryBucketsRecycleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CoffeeGalleryBucketUiState) {
        val bucket = data.bucket
        with(binding) {
            title.text = bucket.bucketName
            selected.isVisible = data.selected
            cover.setImageURI(bucket.coverUri)
            count.text = bucket.count.toString()
        }
    }
}