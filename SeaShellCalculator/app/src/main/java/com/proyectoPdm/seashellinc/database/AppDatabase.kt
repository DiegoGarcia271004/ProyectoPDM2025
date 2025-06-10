package com.proyectoPdm.seashellinc.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyectoPdm.seashellinc.database.dao.MolarMassDao
import com.proyectoPdm.seashellinc.database.dao.UserDao
import com.proyectoPdm.seashellinc.database.entity.MolarMass
import com.proyectoPdm.seashellinc.database.entity.User

@Database(entities = [User::class, MolarMass::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun molarMassDao() : MolarMassDao

    companion object {

        @Volatile private var instance : AppDatabase? = null

        fun getInstance(context: Application) : AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationcontext,
                    AppDatabase::class.java,
                    "SeaShellChemistryDatabase"
                ).build().also { instance = it }
            }

    }
}