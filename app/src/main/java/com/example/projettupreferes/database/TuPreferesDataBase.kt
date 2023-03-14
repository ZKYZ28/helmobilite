package com.example.projettupreferes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.database.dao.ChoiceDao
import com.example.projettupreferes.database.dao.PaireDao
import com.example.projettupreferes.database.dao.StatisticsDao
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.Choice
import com.example.projettupreferes.models.Paire
import com.example.projettupreferes.models.Statistics

//Dans @Database il faut ajouter les classes que l'on veut sotcker
//Si on fait des modifications sur le shéma de la bd il faut augmenter le numéro de la version
@Database(entities = [Category::class, Paire::class, Choice::class, Statistics::class], version = 2, exportSchema = false)
@TypeConverters(TuPreferesTypeConverters::class)
abstract class TuPreferesDataBase : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun choiceDao() : ChoiceDao
    abstract fun paireDao() : PaireDao
    abstract fun statisticsDao() : StatisticsDao


    companion object {
        private const val DATABASE_NAME = "database.db"
        private var instance: TuPreferesDataBase? = null

        fun initDatabase(context: Context) {
            if (instance == null) instance = databaseBuilder(
                context.applicationContext,
                TuPreferesDataBase::class.java, DATABASE_NAME
            )
                .createFromAsset("database.db")
                .build()
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
