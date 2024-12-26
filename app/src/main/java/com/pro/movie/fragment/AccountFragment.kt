package com.pro.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.pro.movie.R
import com.pro.movie.activity.ChangePasswordActivity
import com.pro.movie.activity.SignInActivity
import com.pro.movie.prefs.DataStoreManager
import com.pro.movie.utils.GlobalFunction

class AccountFragment : Fragment() {

    private var mView: View? = null
    private var tvEmail: TextView? = null
    private var layoutChangePassword: LinearLayout? = null
    private var layoutSignOut: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_account, container, false)
        initUi()
        initListener()
        return mView
    }

    private fun initUi() {
        tvEmail = mView?.findViewById(R.id.tv_email)
        layoutChangePassword = mView?.findViewById(R.id.layout_change_password)
        layoutSignOut = mView?.findViewById(R.id.layout_sign_out)
    }

    private fun initListener() {
        tvEmail?.text = DataStoreManager.getUser().getEmail()
        layoutSignOut?.setOnClickListener { onClickSignOut() }
        layoutChangePassword?.setOnClickListener { onClickChangePassword() }
    }

    private fun onClickChangePassword() {
        GlobalFunction.startActivity(activity, ChangePasswordActivity::class.java)
    }

    private fun onClickSignOut() {
        if (activity == null) {
            return
        }
        FirebaseAuth.getInstance().signOut()
        DataStoreManager.setUser(null)
        GlobalFunction.startActivity(activity, SignInActivity::class.java)
        requireActivity().finishAffinity()
    }
}