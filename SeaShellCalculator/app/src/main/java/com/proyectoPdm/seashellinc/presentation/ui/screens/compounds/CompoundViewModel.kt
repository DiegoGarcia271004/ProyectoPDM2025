package com.proyectoPdm.seashellinc.presentation.ui.screens.compounds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.SeaShellChemistryDatabase
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.local.compounds
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import com.proyectoPdm.seashellinc.data.local.compounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompoundViewModel @Inject constructor(
    private val db : SeaShellChemistryDatabase
) : ViewModel() {
    private val _compoundList = MutableStateFlow<List<CompoundEntity>>(emptyList())
    private val _staticCompoundList = MutableStateFlow<List<Compound>>(emptyList())

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val staticCompounds = compounds
                if (compounds.isNotEmpty()) {
                    _staticCompoundList.value = staticCompounds
                } else {
                    _errorMessage.value = "No se ha podido obtener los datos"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }

            try {
                val compound = db.CompoundDao().getCompoundList().firstOrNull()

                if (compound?.isEmpty() == true) {
                    return@launch
                }

                db.CompoundDao().getCompoundList().collect { compoundList ->
                    _compoundList.value = compoundList
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message.toString()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCompound(compoundName: String, static: Boolean = true): Compound? {
        if (static) {
            val compound = _staticCompoundList.value.find {
                it.compoundName.contains(
                    compoundName,
                    ignoreCase = true
                )
            }
            viewModelScope.launch {
                Log.e("Nombre del compuesto:", _staticCompoundList.value.first().toString())
            }
            return if (compound?.compoundName == compoundName) {
                compound
            } else null
        } else {
            val compound = _compoundList.value.find { it.compound.compoundName == compoundName }
            return if (compound?.compound?.compoundName?.contains(
                    compoundName,
                    ignoreCase = true
                ) == true
            ) {
                compound.compound
            } else null
        }
    }

}