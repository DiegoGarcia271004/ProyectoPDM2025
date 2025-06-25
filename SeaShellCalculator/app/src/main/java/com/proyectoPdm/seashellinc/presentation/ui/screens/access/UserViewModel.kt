package com.proyectoPdm.seashellinc.presentation.ui.screens.access

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val repository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _isLoggedUser = MutableStateFlow<Boolean>(false)
    val isLoggedUser = _isLoggedUser.asStateFlow()

    private val _molarMassList = MutableStateFlow<List<CompoundEntity>>(emptyList())
    val molarMassList = _molarMassList.asStateFlow()

    private val _userToken = MutableStateFlow<String?>("")
    val userToken = _userToken.asStateFlow()

    private val _userId = MutableStateFlow<String?>("")
    val userId = _userId.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username = _username.asStateFlow()

    fun setUsername(newUsername : String) {
        _username.value = newUsername
    }

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    fun setEmail(newEmail : String) {
        _email.value = newEmail
    }

    private val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    fun setPassword(newPassword : String) {
        _password.value = newPassword
    }

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String>("")
    val successMessage = _successMessage.asStateFlow()

    private val _accessSuccess = MutableStateFlow<Boolean>(false)
    val accessSuccess = _accessSuccess.asStateFlow()

    fun logout() {
        viewModelScope.launch {

            Log.d("LogoutResponse", "Se ejecuta el logout del usuario")

            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            try {

                repository.logoutUser(userId.value.toString())

                _currentUser.value = null
                _userToken.value = null
                _userId.value = null
                _isLoggedUser.value = false

                _successMessage.value = "Sesion cerrada exitosamente."

            } catch (e : Exception) {
                _errorMessage.value = e.message ?: "Error al cerrar la sesion."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(loginRequest : UserLoginRequest) {

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            val result = repository.loginUser(loginRequest)

            when (result) {
                is Result.Success -> {
                    val userEntity = result.data.first
                    val token = result.data.second

                    _currentUser.value = userEntity
                    _userToken.value = token
                    _userId.value = userEntity.id
                    _isLoggedUser.value = true

                    Log.d("LoginInfo", _currentUser.value.toString())

                    _accessSuccess.value = true
                    _successMessage.value = result.message ?: "Inicio de sesion exitoso."

                    _email.value = ""
                    _password.value = ""
                }

                is Result.Failure -> {

                    _accessSuccess.value = false
                    _errorMessage.value =
                        result.message ?: "Ha ocurrido un error en el inicio de sesion"

                    _email.value = ""
                    _password.value = ""
                }
            }

            _isLoading.value = false
        }
    }

    fun registerUser(userRequest: UserRegisterRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            val result = repository.registerUser(userRequest)

            when (result) {
                is Result.Success -> {

                    val userEntity = result.data

                    _currentUser.value = userEntity
                    _userToken.value = userEntity.token
                    _userId.value = userEntity.id
                    _isLoggedUser.value = true

                    _accessSuccess.value = true
                    _successMessage.value = result.message ?: "Registro de usuario exitoso."

                    _username.value = ""
                    _email.value = ""
                    _password.value = ""
                }

                is Result.Failure -> {

                    _accessSuccess.value = false
                    _errorMessage.value =
                        result.message ?: "Ha ocurrido un error en el registro"

                    _username.value = ""
                    _email.value = ""
                    _password.value = ""
                }
            }

            _isLoading.value = false
        }
    }



    fun clearSuccessOrErrorMessage() {
        _errorMessage.value = ""
        _successMessage.value = ""
    }

    fun resetAccessSuccessState() {
        _accessSuccess.value = false
    }
}