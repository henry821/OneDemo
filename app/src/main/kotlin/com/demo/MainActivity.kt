package com.demo

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_HOME
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.demo.one.R
import com.demo.one.databinding.ActivityMainBinding
import com.demo.utils.TAG_GLOBAL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.toolbar.setupWithNavController(
            navController,
            configuration = AppBarConfiguration.Builder(navController.graph)
                .setOpenableLayout(binding.root)
                .build()
        )
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@MainActivity).setTitle("选择退出方式")
                    .setPositiveButton("完整退出") { _, _ -> finish() }
                    .setNegativeButton("简单退出") { _, _ ->
                        try {
                            startActivity(Intent(ACTION_MAIN).also {
                                it.flags = FLAG_ACTIVITY_NEW_TASK
                                it.addCategory(CATEGORY_HOME)
                            })
                        } catch (e: Exception) {
                            Log.e(TAG_GLOBAL, "简单退出APP: ", e)
                        }
                    }
                    .show()
            }
        })
    }

}