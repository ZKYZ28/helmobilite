package com.example.projettupreferes.database.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.models.Category
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TuPreferesRepository {
    val categoryDao : CategoryDao? = TuPreferesDataBase.getInstance()?.categoryDao()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun insertCategory(category: Category) {
        executor.execute { categoryDao?.insert(category) }
    }

    fun getCategories(): Map<String, Category> {
        val listCategory = mutableListOf<Category>()
        val job = GlobalScope.launch {
            categoryDao?.getCategories()?.collect { categories ->
                Log.d("CATEGORIES", categories.size.toString())
                listCategory.addAll(categories)
            }
        }

        runBlocking {
            job.join()
        }

        if(listCategory.size == 0){
            return emptyMap()
        }

        return listCategory.associateBy { it.categoryName }
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
