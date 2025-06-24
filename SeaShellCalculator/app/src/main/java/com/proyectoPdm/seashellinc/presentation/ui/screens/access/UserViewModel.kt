package com.proyectoPdm.seashellinc.presentation.ui.screens.access

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
import kotlinx.coroutines.flow.collectLatest
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

    init {
        viewModelScope.launch {
            userId.collectLatest { id ->
                id?.let {
                    repository.getUserById(it).collectLatest { user ->
                        _currentUser.value = user
                    }
                    repository.getMolarMassList(it).collectLatest { molarMass ->
                        _molarMassList.value = molarMass
                    }
                }
            }
        }
    }

    fun setAuthUser(userId : String, token : String, user : UserEntity) {
        _userId.value = userId
        _userToken.value = token
        _currentUser.value = user
        _isLoggedUser.value = true
    }

    fun clearAuthUser() {
        viewModelScope.launch {
            repository.clearUserData(userId.value.toString())
        }
        _userId.value = null
        _userToken.value = null
        _currentUser.value = null
        _isLoggedUser.value = false
    }

    fun login(loginRequest : UserLoginRequest, userViewModel: UserViewModel) {

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            val result = repository.loginUser(loginRequest, userViewModel)

            when (result) {
                is Result.Success -> {
                    _accessSuccess.value = true
                    _successMessage.value = result.message ?: "Inicio de sesion exitoso."
                }

                is Result.Failure -> {
                    _accessSuccess.value = false
                    _errorMessage.value =
                        result.message ?: "Ha ocurrido un error en el inicio de sesion"
                }
            }

            _isLoading.value = false
        }
    }

    fun registerUser(userRequest: UserRegisterRequest, userViewModel: UserViewModel) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _successMessage.value = ""

            val result = repository.registerUser(userRequest, userViewModel)

            when (result) {
                is Result.Success -> {
                    _accessSuccess.value = true
                    _successMessage.value = result.message ?: "Registro de usuario exitoso."
                }

                is Result.Failure -> {
                    _accessSuccess.value = false
                    _errorMessage.value =
                        result.message ?: "Ha ocurrido un error en el registro"
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