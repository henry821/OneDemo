package com.demo.fragment

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.demo.one.R
import com.demo.utils.dp
import com.demo.widgets.GestureContainer

/**
 * 模仿录制视频页面
 * Created by hengwei on 2021/3/30.
 */
class GestureFragment : Fragment() {

    private lateinit var tvPosition: TextView
    private lateinit var btnScale: Button
    private lateinit var btnProperties: Button
    private lateinit var gestureContainer: GestureContainer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gesture, container, false)
        tvPosition = root.findViewById(R.id.tv_position)
        btnScale = root.findViewById(R.id.btn_scale)
        btnProperties = root.findViewById(R.id.btn_properties)
        gestureContainer = root.findViewById(R.id.gesture_container)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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