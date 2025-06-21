package com.proyectoPdm.seashellinc.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.proyectoPdm.seashellinc.data.model.Compound
import java.util.UUID

@Entity(tableName = "compoundList")
class CompoundEntity (
    @PrimaryKey(autoGenerate = false) val id : String = UUID.randomUUID().toString(),
    @Embedded val compound : Compound
)
