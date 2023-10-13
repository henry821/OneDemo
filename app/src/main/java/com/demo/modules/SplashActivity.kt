package com.demo.modules

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.demo.modules.main.MainActivity
import com.demo.one.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var loaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreen.setKeepOnScreenCondition {
            /**
             * true展示模式Splash页
             * 反之不展示
             * 最终都会回调[androidx.core.splashscreen.SplashScreen.OnExitAnimationListener.onSplashScreenExit]
             */
            !loaded
        }
        splashScreen.setOnExitAnimationListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        lifecycleScope.launch { loaded = mockLoading() }
    }

    private suspend fun mockLoading(): Boolean {
        withContext(Dispatchers.IO) { delay(1000) }
        return true
    }


}