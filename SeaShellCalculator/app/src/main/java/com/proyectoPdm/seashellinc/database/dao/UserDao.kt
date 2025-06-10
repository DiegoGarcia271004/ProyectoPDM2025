package com.proyectoPdm.seashellinc.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.proyectoPdm.seashellinc.database.entity.MassListForUser
import com.proyectoPdm.seashellinc.database.entity.User
import com.proyectoPdm.seashellinc.database.entity.UserMolarMass

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(user : User) : Long

    @Update
    suspend fun updateUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)

    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun getUserByEmail(email : String) : User?
}