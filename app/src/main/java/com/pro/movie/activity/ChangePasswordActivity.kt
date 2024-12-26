package com.pro.movie.activity

import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.pro.movie.R
import com.pro.movie.model.User
import com.pro.movie.prefs.DataStoreManager
import com.pro.movie.utils.StringUtil

class ChangePasswordActivity : BaseActivity() {

    private var edtOldPassword: EditText? = null
    private var edtNewPassword: EditText? = null
    private var edtConfirmPassword: EditText? = null
    private var btnChangePassword: Button? = null
    private var imgBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        initUi()
        initListener()
    }

    private fun initUi() {
        edtOldPassword = findViewById(R.id.edt_old_password)
        edtNewPassword = findViewById(R.id.edt_new_password)
        edtConfirmPassword = findViewById(R.id.edt_confirm_password)
        btnChangePassword = findViewById(R.id.btn_change_password)
        imgBack = findViewById(R.id.img_back)
    }

    private fun initListener() {
        imgBack?.setOnClickListener { onBackPressed() }
        btnChangePassword?.setOnClickListener { onClickValidateChangePassword() }
    }

    private fun onClickValidateChangePassword() {
        val strOldPassword = edtOldPassword?.text.toString().trim { it <= ' ' }
        val strNewPassword = edtNewPassword?.text.toString().trim { it <= ' ' }
        val strConfirmPassword = edtConfirmPassword?.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strOldPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_old_password_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strNewPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_new_password_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strConfirmPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_confirm_password_require), Toast.LENGTH_SHORT).show()
        } else if (DataStoreManager.getUser().getPassword() != strOldPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_old_password_invalid), Toast.LENGTH_SHORT).show()
        } else if (strNewPassword != strConfirmPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_confirm_password_invalid), Toast.LENGTH_SHORT).show()
        } else if (strOldPassword == strNewPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_new_password_invalid), Toast.LENGTH_SHORT).show()
        } else {
            changePassword(strNewPassword)
        }
    }

    private fun changePassword(newPassword: String) {
        showProgressDialog(true)
        val user = FirebaseAuth.getInstance().currentUser ?: return
        user.updatePassword(newPassword)
                .addOnCompleteListener { task: Task<Void?>? ->
                    showProgressDialog(false)
                    if (task != null && task.isSuccessful) {
                        Toast.makeText(this@ChangePasswordActivity,
                                getString(R.string.msg_change_password_successfully),
                                Toast.LENGTH_SHORT).show()
                        val userLogin: User = DataStoreManager.getUser()
                        userLogin.setPassword(newPassword)
                        DataStoreManager.setUser(userLogin)
                        edtOldPassword?.setText("")
                        edtNewPassword?.setText("")
                        edtConfirmPassword?.setText("")
                    }
                }
    }
}