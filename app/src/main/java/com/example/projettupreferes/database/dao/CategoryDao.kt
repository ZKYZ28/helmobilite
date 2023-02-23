package com.example.projettupreferes.database.dao

import androidx.room.*
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.CategoryWithPaires
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface CategoryDao {

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoriesWithPaires(): Flow<List<CategoryWithPaires>>

    @Query("SELECT * FROM category where id = (:uuid)")
    fun getCategory(uuid: UUID?): Flow<Category>

    @Insert
    fun insertCategory(category: Category?) : Long

    @Update
    fun updateCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category?)

    @Insert
    fun insertPaire(paire: Paire) : Long
}