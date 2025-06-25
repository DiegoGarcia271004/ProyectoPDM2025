package com.proyectoPdm.seashellinc.data.remote

import com.proyectoPdm.seashellinc.data.model.CompoundApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface CompoundApiService {
    @GET(/*Api url*/"/getAll")
    suspend fun getCompounds() : Response<CompoundApiResponse>  //TODO: Configurar que mande como argumentos el correo del usuario y el token de autenticación

    //TODO Hacer el post


    companion object {
        fun create(retrofit: Retrofit) : CompoundApiService {
            return retrofit.create(CompoundApiService::class.java)
        }
    }
}