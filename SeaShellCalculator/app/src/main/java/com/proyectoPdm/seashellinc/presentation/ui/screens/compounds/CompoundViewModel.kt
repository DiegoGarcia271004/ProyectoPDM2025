package com.proyectoPdm.seashellinc.presentation.ui.screens.compounds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.data.model.Compound
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompoundViewModel @Inject constructor(
    private val db : CompoundDatabase
) : ViewModel() {
    private val _compoundList = MutableStateFlow<List<Compound>>(emptyList())

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val compound = db.CompoundDao().getCompoundList().firstOrNull()

                if (compound?.isEmpty() == true){
                    return@launch
                }

                db.CompoundDao().getCompoundList().collect { compoundList ->
                    _compoundList.value = compoundList
                }
            }catch (e : Exception) {
                _errorMessage.value = e.message.toString()
            }
        }
    }

    fun getCompound(compoundName : String): Compound? {
        val compound = _compoundList.value.find { it.compoundName == compoundName }

        return if (compound?.compoundName == compoundName){
            compound
        } else null
    }

}