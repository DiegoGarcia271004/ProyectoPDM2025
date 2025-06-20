package com.proyectoPdm.seashellinc.data.repository

import com.proyectoPdm.seashellinc.data.model.Compound
import com.proyectoPdm.seashellinc.data.remote.CompoundApiService
import com.proyectoPdm.seashellinc.data.model.Result

class CompoundRepository (
    private val apiService: CompoundApiService
) {
    suspend fun getCompoundList() : Result<List<Compound>>{
        return try {
            val response = apiService.getCompounds()
            if (response.isSuccessful) {
                val compounds = response.body()?.message?.map { it.toCompound() } ?: emptyList()
                Result.Success(compounds)
            } else {
                Result.Failure(Exception("Error en la obtención de datos: ${response.code()}"))
            }
        } catch (e : Exception) {
            Result.Failure(e)
        }
    }
}