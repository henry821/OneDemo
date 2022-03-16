package com.demo.activity

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R
import com.demo.utils.dp
import com.demo.widgets.GestureContainer

/**
 * 模仿录制视频页面
 * Created by hengwei on 2021/3/30.
 */
class GestureActivity : AppCompatActivity() {

    private val tvPosition: TextView by lazy { findViewById(R.id.tv_position) }
    private val btnScale: Button by lazy { findViewById(R.id.btn_scale) }
    private val btnProperties: Button by lazy { findViewById(R.id.btn_properties) }
    private val gestureContainer: GestureContainer by lazy { findViewById(R.id.gesture_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)

        gestureContainer.onPositionChangedListener =
            object : GestureContainer.OnPositionChangedListener {
                @SuppressLint("SetTextI18n")
                override fun onPositionChanged(motionEvent: String?, point: PointF) {
                    tvPosition.text = "event:${motionEvent} x:${point.x} y:${point.y}"
                }

            }

        btnScale.setOnClickListener {
            gestureContainer.scaleX = 0.8f
            gestureContainer.scaleY = 0.8f
        }

        btnProperties.setOnClickListener {
            val lp = gestureContainer.layoutParams
            lp.width = 200.dp
            lp.height = 200.dp
            gestureContainer.layoutParams = lp
        }
    }
}