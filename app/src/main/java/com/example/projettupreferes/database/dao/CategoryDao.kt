package com.example.projettupreferes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.Place
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category where id = (:uuid)")
    fun getCategory(uuid: UUID?): Flow<Category>

    @Insert
    fun insertCategory(category: Category?)

    @Update
    fun updateCategory(category: Category)

    /*@Delete
    fun deleteCategory(uuid: UUID?)*/
}