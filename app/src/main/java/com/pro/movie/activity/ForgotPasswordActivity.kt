package com.pro.movie.activity

import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pro.movie.R
import com.pro.movie.utils.StringUtil

class ForgotPasswordActivity : BaseActivity() {

    private var edtEmail: EditText? = null
    private var btnResetPassword: Button? = null
    private var imgBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        initUi()
        initListener()
    }

    private fun initUi() {
        edtEmail = findViewById(R.id.edt_email)
        btnResetPassword = findViewById(R.id.btn_reset_password)
        imgBack = findViewById(R.id.img_back)
    }

    private fun initListener() {
        imgBack?.setOnClickListener { onBackPressed() }
        btnResetPassword?.setOnClickListener { onClickValidateResetPassword() }
    }

    private fun onClickValidateResetPassword() {
        val strEmail = edtEmail?.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(this@ForgotPasswordActivity, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show()
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(this@ForgotPasswordActivity, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show()
        } else {
            resetPassword(strEmail)
        }
    }

    private fun resetPassword(email: String) {
        showProgressDialog(true)
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task: Task<Void?>? ->
                    showProgressDialog(false)
                    if (task != null && task.isSuccessful) {
                        Toast.makeText(this@ForgotPasswordActivity,
                                getString(R.string.msg_reset_password_successfully),
                                Toast.LENGTH_SHORT).show()
                        edtEmail?.setText("")
                    }
                }
    }
}