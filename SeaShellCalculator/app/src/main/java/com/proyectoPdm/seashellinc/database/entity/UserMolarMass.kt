package com.proyectoPdm.seashellinc.database.entity

import androidx.room.Entity

@Entity(
    tableName = "UsuariosMasas",
    primaryKeys = ["userID", "molarMassID"]
)
data class UserMolarMass(
    val userID : String,
    val molarMassID : Long
)