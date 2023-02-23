package com.example.projettupreferes.database.repository

import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TuPreferesRepository {
    val categoryDao : CategoryDao? = TuPreferesDataBase.getInstance()?.categoryDao()
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

//    fun insertCategory(category: Category) {
//        executor.execute { categoryDao?.insertCategory(category) }
//    }

    fun insertCategory(category: Category){
        executor.execute { categoryDao?.insertCategory(category) }
    }

    fun insertPaire(paire: Paire) {
        executor.execute { categoryDao?.insertPaire(paire)}
    }

    fun updatePaire(paire : Paire) {
        executor.execute {categoryDao?.updatePaire(paire)}
    }

    fun updateCategory(category: Category) {
        executor.execute { categoryDao?.updateCategory(category) }
    }

    fun deleteCategory(category: Category) {
        executor.execute { categoryDao?.deleteCategoryWithPaires(category)}
    }

    fun getCategoriesWithPairesList(): Flow<List<CategoryWithPaires>> = categoryDao?.getCategoriesWithPaires() ?: flowOf(emptyList())

    // Dans votre classe de référentiel
    fun getPairesByCategoryId(categoryId: UUID?): Flow<List<Paire>> {
        return categoryDao?.getPairesByCategoryId(categoryId) ?: flowOf(emptyList())
    }

    fun getChoice(choiceId: UUID?): Flow<Choice?> {
        return flow {
            emit(categoryDao?.getChoice(choiceId))
        }.flowOn(Dispatchers.IO)
    }



    fun getCategory(uuid: UUID?): Flow<Category?>? {
        return categoryDao?.getCategory(uuid)
    }

    fun insertChoice(choice: Choice) {
        executor.execute { categoryDao?.insertChoice(choice) }
    }

    fun updateChoice(choice: Choice) {
        executor.execute{categoryDao?.updateChoice(choice)}
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
