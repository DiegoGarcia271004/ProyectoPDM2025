package com.proyectoPdm.seashellinc.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.proyectoPdm.seashellinc.database.entity.MassListForUser
import com.proyectoPdm.seashellinc.database.entity.MolarMass
import com.proyectoPdm.seashellinc.database.entity.UserMolarMass

@Dao
interface MolarMassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMassForUser(userMolarMass : UserMolarMass)

    @Transaction
    @Query("SELECT * FROM usuarios WHERE uid = :usuarioID")
    suspend fun getMassForUser(usuarioID : String) : MassListForUser

    @Query("DELETE FROM UsuariosMasas WHERE userID = :userID AND molarMassID = :massID")
    suspend fun deleteMassForUser(userID : String, massID : Long)
}