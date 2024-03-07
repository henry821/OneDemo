package com.demo.modules.imageview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.demo.one.R

/**
 * Created by hengwei on 2021/4/21.
 */
class ImageViewScaleTypeFragment : Fragment() {

    private lateinit var tvOriginal: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image_view_scale_type, container, false)
        tvOriginal = root.findViewById(R.id.tv_original)
        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = TextView(context).also {
            it.text = """八个ScaleType，其实可以分为三个类型：
           
                    1.以FIT_开头的4种，它们的共同点是都会对图片进行缩放；
                    2.以CENTER_开头的3种，它们的共同点是居中显示，图片的中心点会与ImageView的中心点重叠；
                    3.ScaleType.MATRIX，这种就直接翻到最后看内容吧；"""
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setView(textView)
            .create()

        tvOriginal.setOnClickListener {
            dialog.show()
        }
    }

}