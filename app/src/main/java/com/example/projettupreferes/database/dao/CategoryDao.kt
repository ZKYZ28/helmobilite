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

    @Query("SELECT * FROM category where id = (:uuid)")
    fun getCategory(uuid: UUID?): Flow<Category>

    @Query("SELECT * FROM paire WHERE categoryId = :categoryId")
    fun getPairesByCategoryId(categoryId: UUID?): Flow<List<Paire>>

    @Insert
    fun insertCategory(category: Category?) : Long

    @Update
    fun updateCategory(category: Category)

    @Transaction
    fun deleteCategoryWithPaires(category: Category) {
        deletePaires(category.id)
        deleteCategory(category)
    }

    @Query("DELETE FROM paire WHERE categoryId = :categoryId")
    fun deletePaires(categoryId: UUID)

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

    @Query("SELECT * FROM choice WHERE id = :choiceId")
    fun getChoice(choiceId: UUID?): Choice


}