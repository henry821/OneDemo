package com.demo.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.demo.one.R

/**
 * Created by hengwei on 2022/6/15.
 */
class WindowInsetsFragment : Fragment() {

    private lateinit var btnHideSystemBars: Button
    private lateinit var btnShowSystemBars: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_window_insets, container, false).apply {
            btnHideSystemBars = findViewById(R.id.btn_hide_system_bars)
            btnShowSystemBars = findViewById(R.id.btn_show_system_bars)
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnHideSystemBars.setOnClickListener {
            val controller = it.windowInsetsController
            controller?.hide(WindowInsets.Type.systemBars())
        }

        btnShowSystemBars.setOnClickListener {
            val controller = it.windowInsetsController
            controller?.show(WindowInsets.Type.systemBars())
        }
    }

}