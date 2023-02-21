package com.example.projettupreferes.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.database.dao.TuPreferesDao
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.Place
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TuPreferesRepository {
    val categoryDao : CategoryDao? = TuPreferesDataBase.getInstance()?.categoryDao()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun insertCategory(category: Category) {
        executor.execute { categoryDao?.insert(category) }
    }


    companion object {
        private var instance: TuPreferesRepository? = null

        @JvmStatic //Permet de dire que c'est Static
        fun getInstance(): TuPreferesRepository? {
            if (instance == null) instance = TuPreferesRepository()
            return instance
        }
    }

}
