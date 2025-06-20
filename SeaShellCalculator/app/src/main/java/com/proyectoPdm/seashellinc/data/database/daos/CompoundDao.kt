package com.proyectoPdm.seashellinc.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.model.Compound
import kotlinx.coroutines.flow.Flow

@Dao
interface CompoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompound(compoundEntity: CompoundEntity)

    @Query("SELECT compoundName, chemicalFormula, molarMass FROM compoundList")
    fun getCompoundList(): Flow<List<Compound>>

    @Query("UPDATE compoundList SET compoundName = :compoundName, chemicalFormula = :chemicalFormula, molarMass = :molarMass WHERE id = :postId")
    suspend fun updateCompound(
        compoundName: String,
        chemicalFormula: String,
        molarMass: Double,
        postId: String
    )
    @Query("DELETE FROM compoundList WHERE id = :compoundId")
    suspend fun deleteCompoundById(compoundId: String)
}