package com.proyectoPdm.seashellinc.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuarios")
data class User (
    @PrimaryKey(autoGenerate = true) val uid : Int = 0,
    val email : String,
    val password : String,
    val isPremium : Boolean
)