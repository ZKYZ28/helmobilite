package com.example.projettupreferes.database.repository

import com.example.projettupreferes.database.TuPreferesDataBase
import com.example.projettupreferes.database.dao.CategoryDao
import com.example.projettupreferes.database.dao.ChoiceDao
import com.example.projettupreferes.database.dao.PaireDao
import com.example.projettupreferes.database.dao.StatisticsDao
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
    val choiceDao : ChoiceDao? = TuPreferesDataBase.getInstance()?.choiceDao()
    val paireDao : PaireDao? = TuPreferesDataBase.getInstance()?.paireDao()
    val statisticsDao : StatisticsDao? = TuPreferesDataBase.getInstance()?.statisticsDao()

    val executor: ExecutorService = Executors.newSingleThreadExecutor()


    //CATEGORY
    fun insertCategory(category: Category) {
        executor.execute { categoryDao?.insertCategory(category) }
    }
    fun updateCategory(category: Category) {
        executor.execute { categoryDao?.updateCategory(category) }
    }
    fun deleteCategory(category: Category) {
        executor.execute { categoryDao?.deleteCategory(category)}
    }
    fun getCategoriesWithPairesList(): Flow<List<CategoryWithPaires>> = categoryDao?.getCategoriesWithPaires() ?: flowOf(emptyList())

    fun getCategory(uuid: UUID?): Flow<Category?>? {
        return categoryDao?.getCategory(uuid)
    }

    //PAIRE
    fun insertPaire(paire: Paire) {
        executor.execute {paireDao?.insertPaire(paire)}
    }
    fun deletePaire(idPair: UUID) {
        executor.execute { paireDao?.deletePaire(idPair)}
    }
    fun getPairesByCategoryId(categoryId: UUID?): Flow<List<Paire>> {
        return paireDao?.getPairesByCategoryId(categoryId) ?: flowOf(emptyList())
    }


    //CHOICE
    fun getChoice(choiceId: UUID?): Flow<Choice?> {
        return flow {
            emit(choiceDao?.getChoice(choiceId))
        }.flowOn(Dispatchers.IO)
    }

    fun insertChoice(choice: Choice) {
        executor.execute { choiceDao?.insertChoice(choice) }
    }


    //STATISTICS
    fun updateStatics(statistics: Statistics) {
        executor.execute { statisticsDao?.updateStatistics(statistics) }
    }

    fun getStatistics(statisticsId: UUID?) : Flow<Statistics?>{
        return flow {
            emit(statisticsDao?.getStatistics(statisticsId))
        }.flowOn(Dispatchers.IO)
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
