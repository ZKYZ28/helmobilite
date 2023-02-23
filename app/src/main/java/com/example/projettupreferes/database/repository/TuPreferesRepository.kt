package com.example.projettupreferes.database.repository

import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TuPreferesRepository {
    val categoryDao : CategoryDao? = TuPreferesDataBase.getInstance()?.categoryDao()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun insertCategory(category: Category) {
        executor.execute { categoryDao?.insertCategory(category) }
    }

    fun updateCategory(category: Category) {
        executor.execute { categoryDao?.updateCategory(category) }
    }

    fun deleteCategory(category: Category) {
        executor.execute { categoryDao?.deleteCategory(category)}
    }

    fun getCategoriesList(): Flow<List<Category>> = categoryDao?.getCategories() ?: flowOf(emptyList())

    fun getCategory(uuid: UUID?): Flow<Category?>? {
        return categoryDao?.getCategory(uuid)
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
