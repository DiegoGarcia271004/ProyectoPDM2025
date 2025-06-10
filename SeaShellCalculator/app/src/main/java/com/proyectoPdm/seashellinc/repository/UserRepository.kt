package com.proyectoPdm.seashellinc.repository

import com.proyectoPdm.seashellinc.database.dao.UserDao
import com.proyectoPdm.seashellinc.database.entity.MolarMass
import com.proyectoPdm.seashellinc.database.entity.User
import com.proyectoPdm.seashellinc.database.entity.UserMolarMass

class UserRepository(private val dao : UserDao) {

    suspend fun createNewUser(user : User) {
        dao.inserUser(user)
    }

    suspend fun updateDataUser(user : User) {
        dao.updateUser(user)
    }

    suspend fun deleteUser(user : User) {
        dao.deleteUser(user)
    }

    suspend fun getUserByEmail(email : String) : User? {
        return dao.getUserByEmail(email)
    }
}