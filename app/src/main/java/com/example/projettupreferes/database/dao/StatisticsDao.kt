package com.example.projettupreferes.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.projettupreferes.models.Statistics
import java.util.*
@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics WHERE idStatistics = :statisticsId")
    fun getStatistics(statisticsId: UUID?) : Statistics

    @Update
    fun updateStatistics(statistics: Statistics)

}