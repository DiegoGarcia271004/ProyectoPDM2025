package com.proyectoPdm.seashellinc.repository

import com.proyectoPdm.seashellinc.database.dao.MolarMassDao
import com.proyectoPdm.seashellinc.database.entity.MolarMass
import com.proyectoPdm.seashellinc.database.entity.UserMolarMass

class MolarMassRepository(private val dao : MolarMassDao) {

    suspend fun insertNewMassForUser(uid : String, molarMass : MolarMass) {
        val massForUser = UserMolarMass(
            userID = uid,
            molarMassID = molarMass.id
        )
        dao.insertMassForUser(massForUser)
    }

    suspend fun getMolarMassListForUser(userID : String) : List<MolarMass> {
        return dao.getMassForUser(userID).massList
    }

    suspend fun deleteMassOfListUuser(userID : String, massID : Long) {
        dao.deleteMassForUser(userID, massID)
    }
}