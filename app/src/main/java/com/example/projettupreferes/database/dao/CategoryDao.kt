package com.example.projettupreferes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projettupreferes.models.Category
import com.example.projettupreferes.models.Place
import java.util.concurrent.Flow


@Dao
interface CategoryDao {

   @Query("SELECT * FROM category")
   fun getCategories(): LiveData<List<Category>>

    @Insert
    fun insert(category: Category?)
}