package com.pro.movie.prefs

import android.content.Context
import com.google.gson.Gson
import com.pro.movie.model.User
import com.pro.movie.utils.StringUtil

class DataStoreManager {
    private var sharedPreferences: MySharedPreferences? = null

    companion object {
        private const val PREF_USER_INFO: String = "PREF_USER_INFO"
        private var instance: DataStoreManager? = null

        fun init(context: Context?) {
            instance = DataStoreManager()
            instance?.sharedPreferences = MySharedPreferences(context)
        }

        private fun getInstance(): DataStoreManager? {
            return if (instance != null) {
                instance
            } else {
                throw IllegalStateException("Not initialized")
            }
        }

        fun setUser(user: User?) {
            var jsonUser: String? = ""
            if (user != null) {
                jsonUser = user.toJSon()
            }
            getInstance()?.sharedPreferences?.putStringValue(PREF_USER_INFO, jsonUser)
        }

        fun getUser(): User {
            val jsonUser = getInstance()?.sharedPreferences?.getStringValue(PREF_USER_INFO)
            return if (!StringUtil.isEmpty(jsonUser)) {
                Gson().fromJson(jsonUser, User::class.java)
            } else User()
        }
    }
}