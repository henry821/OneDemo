package com.demo.modules.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.databinding.GalleryBucketsRecycleItemBinding

/**
 * 照片分类Adapter
 */
class CoffeeBucketsAdapter(private val itemClickAction: (CoffeeGalleryBucket) -> Unit) :
    ListAdapter<CoffeeGalleryBucket, BucketViewHolder>(CoffeeGalleryBucket.diffCallback) {
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
    fun bind(data: CoffeeGalleryBucket) {
        with(binding) {
            title.text = data.bucketName
            selected.isVisible = data.selected
            cover.setImageURI(data.coverUri)
            count.text = data.count.toString()
        }
    }
}