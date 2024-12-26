package com.pro.movie.utils

import android.util.Patterns

object StringUtil {
    fun isEmpty(input: String?): Boolean {
        return input.isNullOrEmpty() || "" == input.trim { it <= ' ' }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}