package com.pro.movie.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pro.movie.R
import com.pro.movie.adapter.MyViewPagerAdapter

class MainActivity : BaseActivity() {

    private var mBottomNavigationView: BottomNavigationView? = null
    private var mViewPager2: ViewPager2? = null
    private var tvTitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTitle = findViewById(R.id.tv_title)
        mBottomNavigationView = findViewById(R.id.bottom_navigation)
        mViewPager2 = findViewById(R.id.viewpager_2)
        mViewPager2?.isUserInputEnabled = false
        val myViewPagerAdapter = MyViewPagerAdapter(this)
        mViewPager2?.adapter = myViewPagerAdapter
        mViewPager2?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        mBottomNavigationView?.menu?.findItem(R.id.nav_home)?.isChecked = true
                        tvTitle?.text = getString(R.string.nav_home)
                    }
                    1 -> {
                        mBottomNavigationView?.menu?.findItem(R.id.nav_favorite)?.isChecked = true
                        tvTitle?.text = getString(R.string.nav_favorite)
                    }
                    2 -> {
                        mBottomNavigationView?.menu?.findItem(R.id.nav_history)?.isChecked = true
                        tvTitle?.text = getString(R.string.nav_history)
                    }
                    3 -> {
                        mBottomNavigationView?.menu?.findItem(R.id.nav_account)?.isChecked = true
                        tvTitle?.text = getString(R.string.nav_account)
                    }
                }
            }
        })

        mBottomNavigationView?.setOnItemSelectedListener { item: MenuItem ->
            val id = item.itemId
            when (id) {
                R.id.nav_home -> {
                    mViewPager2?.currentItem = 0
                    tvTitle?.text = getString(R.string.nav_home)
                }
                R.id.nav_favorite -> {
                    mViewPager2?.currentItem = 1
                    tvTitle?.text = getString(R.string.nav_favorite)
                }
                R.id.nav_history -> {
                    mViewPager2?.currentItem = 2
                    tvTitle?.text = getString(R.string.nav_history)
                }
                R.id.nav_account -> {
                    mViewPager2?.currentItem = 3
                    tvTitle?.text = getString(R.string.nav_account)
                }
            }
            true
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showConfirmExitApp()
    }

    private fun showConfirmExitApp() {
        MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive { _: MaterialDialog?, _: DialogAction? -> finish() }
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show()
    }
}