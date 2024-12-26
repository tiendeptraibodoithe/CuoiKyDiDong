package com.pro.movie.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pro.movie.MyApplication
import com.pro.movie.R
import com.pro.movie.model.Movie
import com.pro.movie.model.UserInfor
import com.pro.movie.prefs.DataStoreManager
import com.pro.movie.utils.GlobalFunction

class PlayMovieActivity : BaseActivity() {

    private var progressDialog: MaterialDialog? = null
    private var webView: WebView? = null
    private var mMovieId = 0
    private var mMovie: Movie? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_play_movie)
        getDataIntent()
        initUi()
        getMovieDetail()
    }

    private fun getDataIntent() {
        val bundle = intent.extras ?: return
        mMovieId = bundle.getInt("movie_id")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUi() {
        webView = findViewById(R.id.web_view)
        progressDialog = MaterialDialog.Builder(this)
            .content(R.string.waiting_message)
            .progress(true, 0)
            .build()
        progressDialog?.show()
        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.allowFileAccess = true
        webSettings?.builtInZoomControls = false
        webSettings?.setSupportZoom(false)
        webSettings?.saveFormData = true
        webSettings?.domStorageEnabled = true
        //improve webView performance
        webSettings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webSettings?.useWideViewPort = true
        webView?.webChromeClient = ChromeClient()
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                title = "Loading " + mMovie?.getTitle()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                title = view?.title
                progressDialog?.dismiss()
            }
        }
        val layoutFooter = findViewById<LinearLayout?>(R.id.layout_footer)
        layoutFooter?.setOnClickListener { onBackPressed() }
    }

    private fun getMovieDetail() {
        MyApplication[this].getMovieDetailDatabaseReference(mMovieId)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mMovie = snapshot.getValue(Movie::class.java)
                if (mMovie != null && mMovie!!.getUrl() != null) {
                    webView?.loadUrl(mMovie!!.getUrl()!!)
                    addHistory()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PlayMovieActivity,
                        getString(R.string.msg_get_date_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addHistory() {
        if (mMovie == null || GlobalFunction.isHistory(mMovie)) {
            return
        }
        val userEmail: String? = DataStoreManager.getUser().getEmail()
        val userInfo = UserInfor(System.currentTimeMillis(), userEmail)
        MyApplication[this].getMovieDatabaseReference()
                ?.child(mMovie!!.getId().toString())
                ?.child("history")
                ?.child(userInfo.getId().toString())
                ?.setValue(userInfo)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView?.restoreState(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView?.canGoBack()!!) {
                    webView?.goBack()
                } else {
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class ChromeClient : WebChromeClient() {
        private var mCustomView: View? = null
        private var mCustomViewCallback: CustomViewCallback? = null
        private var mOriginalOrientation = 0
        private var mOriginalSystemUiVisibility = 0
        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomView == null) {
                null
            } else BitmapFactory.decodeResource(applicationContext.resources, 2130837573)
        }

        override fun onHideCustomView() {
            (window.decorView as FrameLayout).removeView(mCustomView)
            mCustomView = null
            window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
            requestedOrientation = mOriginalOrientation
            mCustomViewCallback?.onCustomViewHidden()
            mCustomViewCallback = null
        }

        override fun onShowCustomView(paramView: View?, paramCustomViewCallback: CustomViewCallback?) {
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            mCustomView = paramView
            mOriginalSystemUiVisibility = window.decorView.systemUiVisibility
            mOriginalOrientation = requestedOrientation
            mCustomViewCallback = paramCustomViewCallback
            (window.decorView as FrameLayout).addView(mCustomView, FrameLayout.LayoutParams(-1, -1))
            window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}