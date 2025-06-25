package com.proyectoPdm.seashellinc.di

import android.content.Context
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.data.remote.CompoundApiService
import com.proyectoPdm.seashellinc.data.repository.CompoundRepository
import com.proyectoPdm.seashellinc.utils.ConnectivityHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompoundModule {
    private const val BASE_URL = "https://dog.ceo/api/" //TODO: Agregar la url de la api (investigar como aplicar variables de entorno)

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCompoundApiService(retrofit: Retrofit) : CompoundApiService{
        return retrofit.create(CompoundApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCompoundRepository(compoundApiService: CompoundApiService) : CompoundRepository {
        return CompoundRepository(compoundApiService)
    }

    @Provides
    @Singleton
    fun provideConnectivityHelper(@ApplicationContext context: Context) : ConnectivityHelper{
        return ConnectivityHelper(context)
    }
}
