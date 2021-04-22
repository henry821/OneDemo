package com.demo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R

/**
 * Created by hengwei on 2021/4/21.
 */
class ImageViewScaleTypeActivity : AppCompatActivity() {

    private val tvOriginal: TextView by lazy { findViewById(R.id.tv_original) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_view_scale_type)

        val textView = TextView(this).also {
            it.text = """八个ScaleType，其实可以分为三个类型：
           
                    1.以FIT_开头的4种，它们的共同点是都会对图片进行缩放；
                    2.以CENTER_开头的3种，它们的共同点是居中显示，图片的中心点会与ImageView的中心点重叠；
                    3.ScaleType.MATRIX，这种就直接翻到最后看内容吧；"""
        }
        val dialog = AlertDialog.Builder(this)
                .setView(textView)
                .create()

        tvOriginal.setOnClickListener {
            dialog.show()
        }
    }

}