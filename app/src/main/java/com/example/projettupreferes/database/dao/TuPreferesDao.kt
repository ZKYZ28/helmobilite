package com.example.projettupreferes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projettupreferes.models.Place
import java.util.*

@Dao
interface TuPreferesDao {

    @Query("SELECT * FROM place")
    fun getPlaces() : LiveData<List<Place>>

    @Query("SELECT * FROM place where id = (:uuid)")
    fun getPlace(uuid: UUID?): LiveData<Place>

    @Insert
    fun insert(place: Place)

    @Update
    fun update(place: Place)



}