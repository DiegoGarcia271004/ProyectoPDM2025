package com.proyectoPdm.seashellinc.data.repository

import android.util.Log
import com.google.gson.Gson
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.requests.AddMolarMassRequest
import com.proyectoPdm.seashellinc.data.model.responses.MolarMassData
import com.proyectoPdm.seashellinc.data.model.requests.PasswordRecoveryRequest
import com.proyectoPdm.seashellinc.data.model.requests.ResetPasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdateCredentialsRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePasswordRequest
import com.proyectoPdm.seashellinc.data.model.requests.UpdatePremiumRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserLoginRequest
import com.proyectoPdm.seashellinc.data.model.requests.UserRegisterRequest
import com.proyectoPdm.seashellinc.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.proyectoPdm.seashellinc.data.model.Result
import com.proyectoPdm.seashellinc.data.model.responses.FieldErrorResponse
import com.proyectoPdm.seashellinc.data.model.responses.getErrorMessage
import com.proyectoPdm.seashellinc.presentation.ui.screens.access.UserViewModel
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class UserRepository (
    private val userDao : UserDao,
    private val molarMassDao : CompoundDao,
    private val apiService : ApiService
) {

    private fun parseApiError(errorBody: String?) : String {

        if (errorBody.isNullOrEmpty()) {
            return "Ha ocurrido un error inesperado. Respuesta de error vacía."
        }

        return try {
            val json = JSONObject(errorBody)

            if (json.has("error") && json.get("error") is JSONArray) {
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, FieldErrorResponse::class.java)
                return errorResponse.error.firstOrNull()?.msg
                    ?: "Error de validación: Detalles no disponibles."
            }

            if (json.has("message")) {
                if (json.has("error")) {
                    val errorMessage = json.getString("error")
                    return errorMessage
                }
                return json.getString("message")
            }

            if (json.has("error") && json.get("error") is String) {
                val messagePart = if (json.has("message")) json.getString("message") else ""
                val errorPart = json.getString("error")
                return if (messagePart.isNotBlank() && errorPart.isNotBlank()) {
                    "${messagePart}${errorPart}"
                } else {
                    errorPart
                }
            }

            "Error desconocido: ${errorBody}"

        } catch (e: Exception) {
            return "Error en la comunicación con el servidor. Por favor, inténtelo de nuevo."
        }
    }

    suspend fun registerUser(userRequest : UserRegisterRequest, userViewModel: UserViewModel) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.registerUser(userRequest)
                Log.d("ApiResponse", Gson().toJson(response.body()))
                Log.d("ApiResponse", response.body()?.user?.user?.email ?: "email")
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.molarMassList))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.email))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.username))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.isPremium))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.id))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.token))

                if (response.isSuccessful) {

                    val userResponse = response.body()

                    if (userResponse != null) {
                        val userObjectResponse = userResponse.user

                        val token = userObjectResponse.token
                        val userData = userObjectResponse.user

                        val userEntity = userData.toUserEntity(token)

                        userViewModel.setAuthUser(userData.id, token, userEntity)

                        userDao.deleteAllUsers()
                        userDao.registerUserToDatabase(userEntity)
                        userData.toMolarMassEntity().forEach {
                            molarMassDao.addCompound(it)
                        }

                        return@withContext Result.Success(userEntity, userResponse.message)

                    } else {
                        return@withContext Result.Failure(
                            Exception("Problemas con el servidor, respuesta sin contenido."),
                            "El registro ha fallado: Respuesta vacia."
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error en el registro: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e : Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun loginUser(userRequest : UserLoginRequest, userViewModel: UserViewModel) : Result<Pair<UserEntity, String>> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.loginUser(userRequest)

                Log.d("ApiResponse", Gson().toJson(response.body()))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.molarMassList))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.email))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.username))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.isPremium))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.user?.id))
                Log.d("ApiResponse", Gson().toJson(response.body()?.user?.token))

                if (response.isSuccessful) {

                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        val loginWrapperResponse = loginResponse.user

                        val token = loginWrapperResponse.token
                        val userData = loginWrapperResponse.user

                        val userEntity = userData.toUserEntity(token)

                        userViewModel.setAuthUser(userData.id, token, userEntity)

                        userDao.deleteAllUsers()
                        userDao.registerUserToDatabase(userEntity)
                        userData.toMolarMassEntity().forEach {
                            molarMassDao.addCompound(it)
                        }

                        return@withContext Result.Success(Pair(userEntity, token), loginResponse.message)

                    } else {
                        Result.Failure(
                            Exception("Credenciales de acceso invalidas."),
                            "Credenciales de aceso no validas. Vuelve a intentarlo."
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error en el inicio de sesion: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e : Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    fun getUserById(userId : String) : Flow<UserEntity> {
        return userDao.getUserById(userId)
    }

    suspend fun updateIsPremium(token: String, userId: String, isPremium: Boolean): Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = UpdatePremiumRequest(isPremium)
                val response = apiService.updateIsPremium("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)

                    updatedUser?.let {
                        userDao.updateUser(it)
                        Result.Success(it, updatedUserResponse.message)

                    } ?: Result.Failure(
                        Exception("No se pudo actualizar el estado premium."),
                        "Error en la actualizacion del estado premium. Intenta de nuevo."
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al actualizar estado premium: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun updateCredentials(token : String, userId : String, username : String?, email : String?) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = UpdateCredentialsRequest(username, email)
                val response = apiService.updateCredentialsForUser("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)

                    updatedUser?.let {
                        userDao.updateUser(it)
                        Result.Success(it, updatedUserResponse.message)

                    } ?: Result.Failure(
                        Exception("No se pudieron actualizar las credenciales."),
                        "Error al actualizar las credenciales"
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al actualizar credenciales: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun updatePassword(token : String, userId: String, newPassword : String) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = UpdatePasswordRequest(newPassword)
                val response = apiService.updatePasswordForUser("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)

                    updatedUser?.let {
                        userDao.updateUser(it)
                        Result.Success(it, updatedUserResponse.message)

                    } ?: Result.Failure(
                        Exception("No se pudo actualizar la contraseña."),
                        "Error al actualizar la contraseña"
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al actualizar contraseña: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun requestPasswordRecovery(email : String) : Result<Unit> {

        return withContext(Dispatchers.IO) {
            try {

                val request = PasswordRecoveryRequest(email)
                val response = apiService.requestPasswordRecovery(request)

                if (response.isSuccessful) {

                    Result.Success(Unit, response.body()?.message)

                } else {

                    val errorMessage = response.errorBody()?.string()
                    Result.Failure(
                        Exception("Error al solicitar recuperación: ${errorMessage}"),
                        errorMessage
                    )
                }

            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun resetPassword(token : String, newPassword : String) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = ResetPasswordRequest(token, newPassword)
                val response = apiService.resetPassword(request)

                if (response.isSuccessful) {

                    val userUpdated = response.body()
                    val user = userUpdated?.user?.user?.toUserEntity(token)

                    user?.let {
                        Result.Success(it, userUpdated.message)

                    } ?: Result.Failure(
                        Exception("No se pudo restablecer la contraseña."),
                        "Error al restablecer la contraseña"
                    )

                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al restablecer contraseña: ${errorMessage}"),
                        errorMessage
                    )
                }

            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    fun getMolarMassList(userId : String) : Flow<List<CompoundEntity>> {
        return molarMassDao.getMolarMasses(userId)
    }

    suspend fun getMolarMassList(token : String, userId : String) : Result<List<CompoundEntity>> {
        return try {

            val response = apiService.getMolarMassList("Bearer $token", userId)

            if (response.isSuccessful) {

                val compounds = response.body()?.molarMassList?.map { it.toMolarMassEntity(userId) }
                    ?: emptyList()
                Result.Success(compounds, "Obteniendo lista de masas molares de usuarios")

            } else {

                val errorMessage = parseApiError(response.errorBody()?.string())
                Result.Failure(Exception(errorMessage), errorMessage)
            }
        } catch (e : Exception) {
            Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
        }
    }

    suspend fun refreshMolarMassListFromApi(token : String, userId : String) : Result<Unit> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.getMolarMassList("Bearer $token", userId)

                if (response.isSuccessful) {

                    val molarMasses = response.body()?.molarMassList?.map { it.toMolarMassEntity(userId) }

                    if (!molarMasses.isNullOrEmpty()) {

                        molarMassDao.deleteAllMolarMassForUser(userId)
                        molarMassDao.insertAllCompounds(molarMasses)

                        Result.Success(Unit, "Actualizacion de lista de masas molares exitosa.")

                    } else if (response.body()?.molarMassList?.isEmpty() == true) {

                        molarMassDao.deleteAllMolarMassForUser(userId)
                        Result.Success(Unit, "Vaciando lista de masas molares")

                    } else {
                        Result.Failure(
                            Exception("Lista de masas molares vacía."),
                            "Lista de masas molares vacia"
                        )
                    }

                } else {
                    val errorMessage = getErrorMessage(response)
                    Result.Failure(
                        Exception("Error al obtener lista de masas molares de la API: ${errorMessage}"),
                        errorMessage
                    )
                }

            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun addMolarMassToList(token : String, userId : String, newMolarMass : MolarMassData) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val request = AddMolarMassRequest(newMolarMass)
                val response = apiService.addNewMolarMass("Bearer $token", userId, request)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)
                    val updatedMolarMasses = response.body()?.user?.user?.toMolarMassEntity()

                    if (updatedUser != null && updatedMolarMasses != null) {

                        userDao.updateUser(updatedUser)
                        molarMassDao.deleteAllMolarMassForUser(userId)
                        molarMassDao.insertAllCompounds(updatedMolarMasses)

                        Result.Success(updatedUser, updatedUserResponse.message)

                    } else {
                        Result.Failure(
                            Exception("No se pudo añadir la masa molar."),
                            "Error al agregar la massa molar a la lista"
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al añadir masa molar: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    suspend fun deleteMolarMassFromList(token : String, userId : String, molarMassApiId : String) : Result<UserEntity> {

        return withContext(Dispatchers.IO) {
            try {

                val response = apiService.deleteMolarMass("Bearer $token", userId, molarMassApiId)

                if (response.isSuccessful) {

                    val updatedUserResponse = response.body()
                    val updatedUser = updatedUserResponse?.user?.user?.toUserEntity(token)
                    val updatedMolarMasses = response.body()?.user?.user?.toMolarMassEntity()

                    if (updatedUser != null && updatedMolarMasses != null) {

                        userDao.updateUser(updatedUser)
                        molarMassDao.deleteAllMolarMassForUser(userId)
                        molarMassDao.insertAllCompounds(updatedMolarMasses)

                        Result.Success(updatedUser, updatedUserResponse.message)

                    } else {
                        Result.Failure(
                            Exception("No se pudo eliminar la masa molar."),
                            "Error al eliminar la masa molar de la lista"
                        )
                    }
                } else {
                    val errorMessage = parseApiError(response.errorBody()?.string())
                    Result.Failure(
                        Exception("Error al eliminar masa molar: ${errorMessage}"),
                        errorMessage
                    )
                }
            } catch (e: Exception) {
                Result.Failure(e, e.message ?: "Error de conexion o desconocido.")
            }
        }
    }

    // permite limpiar la sesión local al cerrar sesión
    suspend fun clearUserData(userId : String) {
        userDao.deleteUserById(userId)
        molarMassDao.deleteAllMolarMassForUser(userId)
    }

}