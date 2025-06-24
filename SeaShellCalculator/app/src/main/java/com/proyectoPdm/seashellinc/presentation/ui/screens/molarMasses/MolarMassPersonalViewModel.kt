package com.proyectoPdm.seashellinc.presentation.ui.screens.molarMasses


import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.SeaShellChemistryDatabase
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.LoginScreen
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import com.proyectoPdm.seashellinc.utils.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class MolarMassPersonalViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userRepository: UserRepository,
    private val connectivityHelper: ConnectivityHelper,
    private val db : SeaShellChemistryDatabase
) : ViewModel() {
    private val _compoundList = MutableStateFlow<List<CompoundEntity>>(emptyList())

    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    val filteredList : StateFlow<List<CompoundEntity>> = combine(query, _compoundList) { text, list ->
        if (text.isBlank()) list
        else list.filter { item ->
            item.compound.compoundName.contains(text, ignoreCase = true) || item.compound.chemicalFormula.contains(
                text,
                ignoreCase = true
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                if (connectivityHelper.isNetworkAvailable()){

                    val user = userDao.getLoggedUser()
                    Log.d("GetMolarMassList", user.token)
                    Log.d("GetMolarMassList", user.id)

                    when(val result = userRepository.getMolarMassList(user.token, user.id)){
                        is Result.Success -> {
                            _compoundList.value = result.data
                        }
                        is Result.Failure -> {
                            _errorMessage.value = result.exception.message.toString()
                        }
                    }
                    _compoundList.value.map { item ->
                        db.CompoundDao().addCompound(CompoundEntity(
                            compound = item.compound,
                            id = item.id,
                            userId = item.userId
                        ))
                    }
                } else {
                    try {
                        withTimeout(3_000L) {
                            val result = db.CompoundDao().getCompoundList().firstOrNull()

                            if (result?.isEmpty() == true) {
                                _errorMessage.value = "No hay compuestos en la base local."
                                return@withTimeout
                            }

                            db.CompoundDao().getCompoundList().collect { compoundList ->
                                _compoundList.value = compoundList
                            }
                        }
                    } catch (_: TimeoutCancellationException) {
                        _errorMessage.value = "Tiempo de espera agotado al cargar los datos locales."
                    }
                }
            } catch (e : Exception){
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onValueChange(newQuery : String) {
        _query.value = newQuery
    }

    init {
        loadData()
    }
}