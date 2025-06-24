package com.proyectoPdm.seashellinc.presentation.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.repository.UserRepository
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository : UserRepository
) : ViewModel() {

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

    private val _registerSuccess = MutableStateFlow<Boolean>(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    fun registerUser(userRequest : UserRegisterRequest, userViewModel: UserViewModel) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            val result = repository.registerUser(userRequest, userViewModel)

            when (result) {
                is Result.Success -> {
                    _registerSuccess.value = true
                    _successMessage.value = result.message ?: "Registro de usuario exitoso."
                }
                is Result.Failure -> {
                    _registerSuccess.value = false
                    _errorMessage.value = result.message ?: "Ha ocurrido un error en el registro"
                }
            }

            _isLoading.value = false
        }
    }

    fun clearSuccessOrErrorMessage() {
        _errorMessage.value = ""
        _successMessage.value = ""
    }

    fun resetRegisterSuccessState() {
        _registerSuccess.value = false
    }
}