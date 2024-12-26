package com.pro.movie.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.pro.movie.R
import com.pro.movie.constant.AboutUsConfig
import com.pro.movie.prefs.DataStoreManager
import com.pro.movie.utils.GlobalFunction
import com.pro.movie.utils.StringUtil

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        initUi()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ goToActivity() }, 2000)
    }

    private fun initUi() {
        val tvAboutUsTitle = findViewById<TextView>(R.id.tv_about_us_title)
        val tvAboutUsSlogan = findViewById<TextView>(R.id.tv_about_us_slogan)
    }

    private fun goToActivity() {
        if (!StringUtil.isEmpty(DataStoreManager.getUser().getEmail())) {
            GlobalFunction.startActivity(this, MainActivity::class.java)
        } else {
            GlobalFunction.startActivity(this, SignInActivity::class.java)
        }
        finish()
    }
}