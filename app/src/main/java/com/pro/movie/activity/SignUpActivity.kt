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

class SignUpActivity : BaseActivity() {

    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var btnSignUp: Button? = null
    private var layoutSignIn: LinearLayout? = null
    private var imgBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initUi()
        initListener()
    }

    private fun initUi() {
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btn_sign_up)
        layoutSignIn = findViewById(R.id.layout_sign_in)
        imgBack = findViewById(R.id.img_back)
    }

    private fun initListener() {
        imgBack?.setOnClickListener { onBackPressed() }
        layoutSignIn?.setOnClickListener { finish() }
        btnSignUp?.setOnClickListener { onClickValidateSignUp() }
    }

    private fun onClickValidateSignUp() {
        val strEmail = edtEmail?.text.toString().trim { it <= ' ' }
        val strPassword = edtPassword?.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(this@SignUpActivity, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(this@SignUpActivity, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show()
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(this@SignUpActivity, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show()
        } else {
            signUpUser(strEmail, strPassword)
        }
    }

    private fun signUpUser(email: String, password: String) {
        showProgressDialog(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task: Task<AuthResult?>? ->
                    showProgressDialog(false)
                    if (task != null && task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            val userObject = User(user.email, password)
                            DataStoreManager.setUser(userObject)
                            GlobalFunction.startActivity(this@SignUpActivity, MainActivity::class.java)
                            finishAffinity()
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, getString(R.string.msg_sign_up_error),
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}