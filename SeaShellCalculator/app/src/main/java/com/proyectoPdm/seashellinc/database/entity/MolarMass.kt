package com.proyectoPdm.seashellinc.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "MasasMolares")
data class MolarMass (
    @PrimaryKey(autoGenerate = true) val id : Long,
    val compoundName : String,
    val formula : String?,
    val molarMass : Double,
)