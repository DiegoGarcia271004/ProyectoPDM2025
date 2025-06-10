package com.proyectoPdm.seashellinc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyectoPdm.seashellinc.database.AppDatabase
import com.proyectoPdm.seashellinc.database.entity.User
import com.proyectoPdm.seashellinc.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository : UserRepository

    init {
        val db = AppDatabase.getInstance(application)
        val userDao = db.userDao()
        userRepository = UserRepository(userDao)
    }

    private val _user = MutableStateFlow<User?>(null)
    val user : StateFlow<User?> get() = _user //se usara para manejar datos del usuario dentro de los composables

    fun createUser(user : User) {
        viewModelScope.launch {
            userRepository.createNewUser(user)
            _user.value = user
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateDataUser(user)
            _user.value = user
        }
    }

    fun deleteUser(user : User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
            _user.value = null
        }
    }

    fun getUserByEmail(email : String) { //metodo para cuando el login haya indicado que el usuario es valido
        //asi se inicializa dentro de la aplicacion con la informacion de dicho usuario
        viewModelScope.launch {
            val result = userRepository.getUserByEmail(email)
            _user.value = result
        }
    }

}