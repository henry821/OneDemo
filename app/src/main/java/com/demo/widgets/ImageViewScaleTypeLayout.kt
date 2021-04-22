package com.demo.widgets

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.demo.one.R

/**
 * Created by hengwei on 2021/4/22.
 */
class ImageViewScaleTypeLayout : ConstraintLayout {

    companion object {
        val desc = listOf(
                "ScaleType.MATRIX : 该模式需要与ImageView.setImageMatrix(Matrix matrix) 配合使用，因为该模式需要用于指定一个变换矩阵用于指定图片如何展示。其实前面的7种模式都是通过ImageView在内部生成了相应的变换矩阵，等于是提供了该模式的一种特定值，使用这个模式只要传入相应矩阵，也就能实现上述七种显示效果。",
                "ScaleType.FIT_XY : 图片缩放到控件大小，完全填充控件大小展示。注意，此模式不是等比缩放。",
                "ScaleType.FIT_START : 图片等比缩放到控件大小，并放置在控件的上边或左边展示。如图所示，此模式下会在ImageView的下半部分留白，如果图片高度大于宽，那么就会在ImageView的右半部份留白。",
                "ScaleType.FIT_CENTER : 该模式是ImageView的默认模式，图片会被等比缩放到能够填充控件大小，并居中展示。",
                "ScaleType.FIT_END : 图片等比缩放到控件大小，并放置在控件的下边或右边展示。如图所示，此模式下会在ImageView的上半部分留白，如果图片高度大于宽，那么就会在ImageView的左半部分留白。",
                "ScaleType.CENTER : 不使用缩放，ImageView会展示图片的中心部分，即图片的中心点和ImageView的中心点重叠，如图。如果图片的大小小于控件的宽高，那么图片会被居中显示。",
                "ScaleType.CENTER_CROP : 图片会被等比缩放直到完全填充整个ImageView，并居中显示。",
                "ScaleType.CENTER_INSIDE : 使用此模式以完全展示图片的内容为目的。图片将被等比缩放到能够完整展示在ImageView中并居中，如果图片大小小于控件大小，那么就直接居中展示该图片。"
        )
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ImageViewScaleTypeLayout)
        scaleTypeIndex = a.getInt(R.styleable.ImageViewScaleTypeLayout_scaleType, -1)
        a.recycle()

        inflate(context, R.layout.layout_image_scale_type_item, this)

        scaleTypeIndex.scaleType().run {
            ivBig.scaleType = this
            ivSmall.scaleType = this
            tvName.text = this.name
        }

        val textView = TextView(context)

        dialog = AlertDialog.Builder(context)
                .setView(textView)
                .create()

        setOnClickListener {
            textView.text = scaleTypeIndex.desc()
            dialog.show()
        }
    }

    private val ivBig: ImageView by lazy { findViewById(R.id.iv_big) }
    private val ivSmall: ImageView by lazy { findViewById(R.id.iv_small) }
    private val tvName: TextView by lazy { findViewById(R.id.tv_name) }
    private var scaleTypeIndex: Int = -1
    private var dialog: Dialog

    private fun Int.scaleType(): ImageView.ScaleType {
        return when (this) {
            0 -> ImageView.ScaleType.MATRIX
            1 -> ImageView.ScaleType.FIT_XY
            2 -> ImageView.ScaleType.FIT_START
            3 -> ImageView.ScaleType.FIT_CENTER
            4 -> ImageView.ScaleType.FIT_END
            5 -> ImageView.ScaleType.CENTER
            6 -> ImageView.ScaleType.CENTER_CROP
            7 -> ImageView.ScaleType.CENTER_INSIDE
            else -> ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun Int.desc(): String {
        return if (this in 0..7) {
            desc[this]
        } else {
            desc[0]
        }
    }

}