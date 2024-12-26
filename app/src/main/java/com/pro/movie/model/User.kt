package com.pro.movie.model

import com.google.gson.Gson

class User {

    private var email: String? = null
    private var password: String? = null

    constructor() {}
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun toJSon(): String? {
        val gson = Gson()
        return gson.toJson(this)
    }
}