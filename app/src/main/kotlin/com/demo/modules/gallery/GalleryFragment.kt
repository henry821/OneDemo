package com.demo.modules.gallery

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.modules.gallery.GalleryFragment.Builder
import com.demo.one.R
import com.demo.one.databinding.GalleryFragmentBinding
import com.demo.utils.GridSpacingItemDecoration
import com.demo.utils.dp
import com.demo.utils.launchAndRepeatWithViewLifecycle

/**
 * 相册页面
 *
 * 1. 使用[GalleryViewModel]加载图片和目录列表
 * 2. 使用[Builder]创建实例，示例：
 * ```
 *   CoffeeGalleryFragment.Builder()
 *      .enableCapture() //展示拍摄条目
 *      .clickItemAction { item, _ -> cropLauncher.launch(item.contentUri) } //图片条目点击事件
 *      .captureAction {  } //拍摄条目点击事件
 *      .fragmentManager(childFragmentManager)
 *      .lifecycleOwner(viewLifecycleOwner)
 *      .add()
 * ```
 */
class GalleryFragment : Fragment(R.layout.gallery_fragment) {

    private lateinit var binding: GalleryFragmentBinding
    private val galleryVM by activityViewModels<GalleryViewModel>(
        factoryProducer = { GalleryViewModel.ViewModelFactory(enableCapture) }
    )

    private val enableCapture by lazy { arguments?.getBoolean(KEY_ENABLE_CAPTURE) ?: false }

    private val galleryAdapter = GalleryAdapter(
        captureAction = { setFragmentResult(KEY_ACTION_CAPTURE, bundleOf()) },
        itemClickAction = {
            setFragmentResult(
                KEY_ACTION_CLICK_ITEM,
                bundleOf(KEY_DATA_GALLERY_IMAGE to it)
            )
        }
    )
    private val bucketAdapter = GalleryBucketsAdapter { galleryVM.selectBucket(it.bucket.bucketId) }

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        galleryVM.loadImages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GalleryFragmentBinding.bind(view)

        launcher.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
            else Manifest.permission.READ_EXTERNAL_STORAGE
        )

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
                imagesState.collect {
                    galleryAdapter.submitList(it)
                }
            }
            launchAndRepeatWithViewLifecycle {
                bucketsState.collect { list ->
                    val selected = list.firstOrNull { it.selected }
                    binding.bucket.text = selected?.bucket?.bucketName
                    bucketAdapter.submitList(list)
                    binding.root.transitionToStart()
                }
            }
        }
    }

    companion object {
        private const val COLUMN = 3
        private const val KEY_ENABLE_CAPTURE = "key_enable_capture" //是否支持拍照

        const val KEY_ACTION_CAPTURE = "key_action_capture"
        const val KEY_ACTION_CLICK_ITEM = "key_action_click_item"
        const val KEY_DATA_GALLERY_IMAGE = "key_data_gallery_image"
    }

    class Builder {
        private var enableCapture: Boolean = false //是否展示拍摄按钮
        private var captureAction: (Fragment) -> Unit = {} //拍摄按钮点击操作
        private var clickItemAction: (CoffeeGalleryImage, Fragment) -> Unit =
            { _, _ -> } //图片条目点击操作
        private lateinit var manager: FragmentManager
        private lateinit var lifecycleOwner: LifecycleOwner

        fun fragmentManager(manager: FragmentManager) = apply { this.manager = manager }
        fun lifecycleOwner(owner: LifecycleOwner) = apply { lifecycleOwner = owner }

        /**
         * 是否显示拍照条目
         */
        fun enableCapture() = apply { enableCapture = true }

        /**
         * 拍摄按钮点击操作
         */
        fun captureAction(action: (Fragment) -> Unit) = apply { captureAction = action }

        /**
         * 图片条目点击操作
         */
        fun clickItemAction(action: (CoffeeGalleryImage, Fragment) -> Unit) =
            apply { clickItemAction = action }

        fun add(): Fragment {
            val fragment = GalleryFragment()
            with(manager) {
                setFragmentResultListener(KEY_ACTION_CAPTURE, lifecycleOwner) { requestKey, _ ->
                    if (requestKey == KEY_ACTION_CAPTURE) captureAction.invoke(fragment)
                }
                setFragmentResultListener(KEY_ACTION_CLICK_ITEM, lifecycleOwner) { key, result ->
                    if (key == KEY_ACTION_CLICK_ITEM) {
                        BundleCompat.getParcelable(
                            result,
                            KEY_DATA_GALLERY_IMAGE,
                            CoffeeGalleryImage::class.java
                        )?.also { clickItemAction.invoke(it, fragment) }
                    }
                }
                beginTransaction().add(fragment.also {
                    it.arguments = bundleOf(KEY_ENABLE_CAPTURE to enableCapture)
                }, "").commit()
            }
            return fragment
        }

    }
}