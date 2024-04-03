package com.demo.modules.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.one.databinding.GalleryFragmentBinding
import com.demo.utils.GridSpacingItemDecoration
import com.demo.utils.dp
import com.demo.utils.launchAndRepeatWithViewLifecycle

/**
 * 相册页面
 */
class GalleryFragment : Fragment(R.layout.gallery_fragment) {

    private lateinit var binding: GalleryFragmentBinding
    private val galleryVM by activityViewModels<GalleryViewModel>()
    private val galleryAdapter = CoffeeGalleryAdapter()
    private val bucketAdapter = CoffeeBucketsAdapter { galleryVM.selectBucket(it.bucketId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GalleryFragmentBinding.bind(view)

        with(binding.gallery) {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), COLUMN)
            addItemDecoration(GridSpacingItemDecoration(COLUMN, 4.dp, false))
        }

        with(binding.buckets) {
            adapter = bucketAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            itemAnimator = null
        }

        with(galleryVM) {
            launchAndRepeatWithViewLifecycle {
                images.collect {
                    galleryAdapter.submitList(it)
                }
            }
            launchAndRepeatWithViewLifecycle {
                buckets.collect { list ->
                    val selected = list.firstOrNull { it.selected }
                    binding.bucket.text = selected?.bucketName
                    bucketAdapter.submitList(list)
                    binding.root.transitionToStart()
                }
            }
        }
    }

    companion object {
        private const val COLUMN = 3
    }
}