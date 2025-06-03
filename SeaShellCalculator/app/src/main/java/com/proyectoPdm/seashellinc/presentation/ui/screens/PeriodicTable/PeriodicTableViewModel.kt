package com.proyectoPdm.seashellinc.presentation.ui.screens.PeriodicTable

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.local.elements
import com.proyectoPdm.seashellinc.data.local.model.Element
import kotlinx.coroutines.launch

class PeriodicTableViewModel() : ViewModel() {
    private val _elementList = mutableStateOf<List<Element>>(emptyList())
    val elementList: State<List<Element>> = _elementList

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String>("")
    val errorMessage: State<String?> = _errorMessage

    init {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val listOfElements = elements
                if (!listOfElements.isEmpty()) {
                    _elementList.value = listOfElements
                } else {
                    _errorMessage.value = "Ha ocurrido un error al llamar los datos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error. ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}