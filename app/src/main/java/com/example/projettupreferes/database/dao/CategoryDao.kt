package com.example.projettupreferes.database.dao

import androidx.room.*
import com.example.projettupreferes.models.*
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface CategoryDao {

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoriesWithPaires(): Flow<List<CategoryWithPaires>>

    @Query("SELECT categoryName FROM category WHERE categoryName = (:categoryName)")
    fun getCategoryName(categoryName : String): String


    @Query("SELECT * FROM category where idCategory = (:uuid)")
    fun getCategory(uuid: UUID?): Flow<Category>

    @Query("SELECT * FROM paire WHERE categoryIdFk = :categoryId")
    fun getPairesByCategoryId(categoryId: UUID?): Flow<List<Paire>>

    @Insert
    fun insertCategory(category: Category?) : Long

    @Update
    fun updateCategory(category: Category)


    @Query("DELETE FROM paire WHERE idPaire = :idPaire")
    fun deletePaire(idPaire: UUID)

    @Delete
    fun deleteCategory(category: Category)


    @Insert
    fun insertChoice(choice: Choice) : Long

    @Update
    fun updateChoice(choice: Choice)

    @Insert
    fun insertPaire(paire: Paire)

    @Update
    fun updatePaire(paire: Paire)

    @Query("SELECT * FROM choice WHERE idChoice = :choiceId")
    fun getChoice(choiceId: UUID?): Choice


    @Query("SELECT * FROM statistics WHERE idStatistics = :statisticsId")
    fun getStatistics(statisticsId: UUID?) : Statistics

    @Update
    fun updateStatistics(statistics: Statistics)

}