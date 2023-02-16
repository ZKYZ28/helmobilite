package com.example.projettupreferes.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projettupreferes.database.dao.TuPreferesDao
import com.example.projettupreferes.models.Place

@Database(entities = [Place::class], version = 1, exportSchema = false)
@TypeConverters(*[TuPreferesTypeConverters::class])
abstract class TuPreferesDataBase : RoomDatabase() {
    abstract fun tuPreferesDao(): TuPreferesDao

    companion object {
        private const val DATABASE_NAME = "tu-preferes-database"
        private var instance: TuPreferesDataBase? = null

        fun initDatabase(context: Context) {
            if (instance == null) instance = databaseBuilder(
                context.applicationContext,
                TuPreferesDataBase::class.java, DATABASE_NAME
            ).build()
            Log.d("BD", "initialisation de la bdd")
        }

        fun getInstance(): TuPreferesDataBase? {
            checkNotNull(instance) { "Tu préfères database must be initialized" }
            return instance
        }

        fun disconnectDatabase() {
            instance!!.close()
            instance = null
        }
    }
}
