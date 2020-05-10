package com.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.beans.TurnPlateViewItemBean
import com.demo.one.R
import com.demo.widgets.TurnPlateView

/**
 * Description 展示自定义布局
 * Author wanghengwei
 * Date   2020/5/9
 */
class CustomViewFragment : BaseFragment() {

    private lateinit var turnPlatView: TurnPlateView
    private lateinit var turnPlateData: List<TurnPlateViewItemBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        turnPlateData = arrayListOf(TurnPlateViewItemBean("1", "太阳", "120", "#ff0000"),
                TurnPlateViewItemBean("2", "草原", "120", "#00ff00"),
                TurnPlateViewItemBean("3", "大海", "120", "#0000ff"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_custom_view, container, false)
        turnPlatView = root.findViewById(R.id.turnPlatView)
        turnPlatView.let {
            it.initData(turnPlateData)
            it.setOnClickListener(object : TurnPlateView.OnClickListener {
                override fun onClickPlate() {
                    it.start(2)
                }

                override fun onClose() {
                }

            })
        }
        return root
    }

}