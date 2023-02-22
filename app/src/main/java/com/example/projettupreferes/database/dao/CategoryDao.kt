package com.example.projettupreferes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projettupreferes.models.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Insert
    fun insert(category: Category?)
}