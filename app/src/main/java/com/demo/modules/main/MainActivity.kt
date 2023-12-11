package com.demo.modules.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_HOME
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.demo.base.GlobalGray
import com.demo.one.R
import com.demo.one.databinding.ActivityMainBinding
import com.demo.widgets.OverviewFloatView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var overviewFloatView: OverviewFloatView
    private lateinit var navController: NavController

    private val mainVM by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, binding.root)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        binding.navigationView.menu.findItem(R.id.global_gray).setOnMenuItemClickListener {
            GlobalGray.enable()
            true
        }

        mainVM.fps.observe(this) { overviewFloatView.updateFps(it) }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        overviewFloatView = OverviewFloatView(this)
        overviewFloatView.show()
    }

    override fun onResume() {
        super.onResume()
//        mainVM.watchFps()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.root)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setTitle("选择退出方式")
            .setPositiveButton("完整退出") { _, _ -> finish() }
            .setNegativeButton("简单退出") { _, _ -> appBackToBackground(this) }
            .show()
    }

    // appBackToBackground
    private fun appBackToBackground(activity: Activity) {
        safeThrowable {
            activity.startActivity(Intent(ACTION_MAIN).also {
                it.flags = FLAG_ACTIVITY_NEW_TASK
                it.addCategory(CATEGORY_HOME)
            })
        }
    }

    private inline fun <T> safeThrowable(method: () -> T?): T? {
        try {
            return method()
        } catch (e: Throwable) {
            Log.e("safeThrowable", e.message.toString())
        }
        return null
    }

}