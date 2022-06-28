package com.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.demo.one.R
import com.demo.widgets.OverviewFloatView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val drawerLayout: DrawerLayout by lazy { findViewById(R.id.drawer) }
    private lateinit var overviewFloatView: OverviewFloatView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        NavigationUI.setupWithNavController(navigationView, navController)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        overviewFloatView = OverviewFloatView(this)
        overviewFloatView.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}