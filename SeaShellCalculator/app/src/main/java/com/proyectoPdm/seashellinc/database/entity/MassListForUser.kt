package com.proyectoPdm.seashellinc.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MassListForUser (
    @Embedded val user : User,
    @Relation (
        parentColumn = "uid",
        entityColumn = "id",
        associateBy = Junction(UserMolarMass::class)
    )
    val massList : List<MolarMass>
)