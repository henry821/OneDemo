package com.demo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R
import com.demo.widgets.OverviewFloatView

class MainActivity : AppCompatActivity() {

    private var overviewFloatView: OverviewFloatView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_lottie).setOnClickListener {
            startActivity(Intent(this, LottieActivity::class.java))
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        overviewFloatView = OverviewFloatView(this)
        overviewFloatView?.show()
    }

}