package com.hovi.hoco.model

import com.google.firebase.auth.FirebaseUser

/**
 * Created by VietTC on 12/12/2020.
 */
class User {
    var userName = ""
    var password = ""
    var connectionString = ""
    var isAdmin = false

    constructor(userName: String, password: String, connectionString: String) {
        this.userName = userName
        this.password = password
        this.connectionString = connectionString
    }

    constructor(firebaseUser: FirebaseUser) {
        firebaseUser.email?.let {
            userName = getUserNameFromEmail(it)
        }
    }
}

fun getUserNameFromEmail(email: String): String {
    val index = email.indexOf(".")
    return if(index > -1) {
        email.substring(0, index)
    } else {
        email
    }
}