package com.proyectoPdm.seashellinc.di

import android.content.Context
import androidx.room.Room
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : CompoundDatabase {
        return Room.databaseBuilder(
            context,
            CompoundDatabase::class.java,
            "CompoundDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}