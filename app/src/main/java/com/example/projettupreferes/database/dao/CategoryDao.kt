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


    @Insert
    fun insertCategory(category: Category?) : Long

    @Update
    fun updateCategory(category: Category)


    @Delete
    fun deleteCategory(category: Category)

}