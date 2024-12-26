package com.pro.movie.model

import java.io.Serializable

class UserInfor : Serializable {

    private var id: Long = 0
    private var emailUser: String? = null

    constructor() {}
    constructor(id: Long, emailUser: String?) {
        this.id = id
        this.emailUser = emailUser
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getEmailUser(): String? {
        return emailUser
    }

    fun setEmailUser(emailUser: String?) {
        this.emailUser = emailUser
    }
}