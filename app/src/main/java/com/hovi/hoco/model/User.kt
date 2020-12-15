package com.hovi.hoco.model

/**
 * Created by VietTC on 12/12/2020.
 */
class User {
    var userName = ""
    var password = ""
    var connectionString = ""

    constructor(userName: String, password: String, connectionString: String) {
        this.userName = userName
        this.password = password
        this.connectionString = connectionString
    }
}