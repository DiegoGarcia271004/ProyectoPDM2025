package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.local.compounds
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MolarMassViewModel @Inject constructor() : ViewModel() {
    private val _compoundList = MutableStateFlow<List<Compound>>(emptyList())

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading : State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage : State<String?> = _errorMessage

    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    val filteredList : StateFlow<List<Compound>> = combine(query, _compoundList) { text, list ->
        if (text.isBlank()) list
        else list.filter { item ->
            item.compoundName.contains(text, ignoreCase = true) || item.chemicalFormula?.contains(
                text,
                ignoreCase = true
            ) == true
        }
    }.stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                val listOfCompounds = compounds
                if (compounds.isNotEmpty()) {
                    _compoundList.value = listOfCompounds
                } else {
                    _errorMessage.value = "No se ha podido obtener los datos"
                }

            } catch (e : Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onValueChange(newQuery : String) {
        _query.value = newQuery
    }
}