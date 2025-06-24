package com.proyectoPdm.seashellinc.data.model.user

class AuthInfo {

    private lateinit var id : String
    private lateinit var token : String

    fun getId() : String {
        return id
    }

    fun getToken() : String {
        return token
    }
}