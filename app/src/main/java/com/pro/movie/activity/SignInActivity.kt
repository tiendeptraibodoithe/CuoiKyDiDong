package com.pro.movie.activity

import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pro.movie.R
import com.pro.movie.model.User
import com.pro.movie.prefs.DataStoreManager
import com.pro.movie.utils.GlobalFunction
import com.pro.movie.utils.StringUtil

class SignInActivity : BaseActivity() {

    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var tvForgotPassword: TextView? = null
    private var layoutSignUp: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initUi()
        initListener()
    }

    private fun initUi() {
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        layoutSignUp = findViewById(R.id.layout_sign_up)
    }

    private fun initListener() {
        layoutSignUp?.setOnClickListener { GlobalFunction.startActivity(this@SignInActivity, SignUpActivity::class.java) }
        btnSignIn?.setOnClickListener { onClickValidateSignIn() }
        tvForgotPassword?.setOnClickListener { onClickForgotPassword() }
    }

    private fun onClickForgotPassword() {
        GlobalFunction.startActivity(this, ForgotPasswordActivity::class.java)
    }

    private fun onClickValidateSignIn() {
        val strEmail = edtEmail?.text.toString().trim { it <= ' ' }
        val strPassword = edtPassword?.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(this@SignInActivity, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(this@SignInActivity, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show()
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(this@SignInActivity, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show()
        } else {
            signInUser(strEmail, strPassword)
        }
    }

    private fun signInUser(email: String, password: String) {
        showProgressDialog(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task: Task<AuthResult?>? ->
                    showProgressDialog(false)
                    if (task != null && task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            val userObject = User(user.email, password)
                            DataStoreManager.setUser(userObject)
                            GlobalFunction.startActivity(this@SignInActivity, MainActivity::class.java)
                            finishAffinity()
                        }
                    } else {
                        Toast.makeText(this@SignInActivity, getString(R.string.msg_sign_in_error),
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}