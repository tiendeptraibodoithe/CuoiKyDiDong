package com.pro.movie.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import java.text.Normalizer
import java.util.regex.Pattern

object Utils {

    fun hideSoftKeyboard(activity: Activity?) {
        try {
            val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }
    }

    fun getTextSearch(input: String?): String? {
        val nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(nfdNormalizedString).replaceAll("")
    }
}