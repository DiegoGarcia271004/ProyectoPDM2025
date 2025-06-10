package com.proyectoPdm.seashellinc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.database.AppDatabase
import com.proyectoPdm.seashellinc.database.entity.MolarMass
import com.proyectoPdm.seashellinc.repository.MolarMassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MolarMassViewModel(application: Application) : AndroidViewModel(application) {

    private val molarMassRepository : MolarMassRepository

    init {
        val db = AppDatabase.getInstance(application)
        val molarMassDao = db.molarMassDao()
        molarMassRepository = MolarMassRepository(molarMassDao)
    }

    private val _molarMass = MutableStateFlow<List<MolarMass>>(emptyList())
    val molarMass : StateFlow<List<MolarMass>> = _molarMass //se usara en el composable de la lista de masas para usuario

    fun loadMolarMassForUser(uid : String) {
        viewModelScope.launch {
            _molarMass.value = molarMassRepository.getMolarMassListForUser(uid)
        }
    }

    fun addMolarMassToListUser(uid : String, molarMass : MolarMass) {
        viewModelScope.launch {
            molarMassRepository.insertNewMassForUser(uid, molarMass)
            loadMolarMassForUser(uid)
        }
    }

    fun deleteMolarMassOfListUser(userID : String, massID : Long) {
        viewModelScope.launch {
            molarMassRepository.deleteMassOfListUuser(userID, massID)
            loadMolarMassForUser(userID)
        }
    }
}