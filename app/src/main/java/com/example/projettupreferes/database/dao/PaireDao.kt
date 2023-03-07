package com.example.projettupreferes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projettupreferes.models.Paire
import kotlinx.coroutines.flow.Flow
import java.util.*
@Dao
interface PaireDao {

    @Query("SELECT * FROM paire WHERE categoryIdFk = :categoryId")
    fun getPairesByCategoryId(categoryId: UUID?): Flow<List<Paire>>

    @Query("DELETE FROM paire WHERE idPaire = :idPaire")
    fun deletePaire(idPaire: UUID)

    @Insert
    fun insertPaire(paire: Paire)

    @Update
    fun updatePaire(paire: Paire)
}