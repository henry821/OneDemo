package com.demo.modules.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.databinding.GalleryRecycleItemBinding

class CoffeeGalleryAdapter :
    ListAdapter<CoffeeGalleryImage, GalleryViewHolder>(CoffeeGalleryImage.diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryRecycleItemBinding.inflate(inflater, parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class GalleryViewHolder(private val binding: GalleryRecycleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CoffeeGalleryImage) {
        binding.root.setImageURI(data.contentUri)
    }
}