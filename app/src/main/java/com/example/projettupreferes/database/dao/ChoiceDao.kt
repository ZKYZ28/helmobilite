package com.example.projettupreferes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projettupreferes.models.Choice
import java.util.*
@Dao
interface ChoiceDao {
    @Insert
    fun insertChoice(choice: Choice) : Long

    @Query("SELECT * FROM choice WHERE idChoice = :choiceId")
    fun getChoice(choiceId: UUID?): Choice
}