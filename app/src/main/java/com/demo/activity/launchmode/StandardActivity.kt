package com.demo.activity.launchmode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.one.R

class StandardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_mode)
        supportActionBar?.title = javaClass.simpleName
    }
}
