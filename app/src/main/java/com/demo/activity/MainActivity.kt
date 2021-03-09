package com.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R
import com.demo.widgets.OverviewFloatView

class MainActivity : AppCompatActivity() {

    private var overviewFloatView: OverviewFloatView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        overviewFloatView = OverviewFloatView(this)
        overviewFloatView?.show()
    }

}