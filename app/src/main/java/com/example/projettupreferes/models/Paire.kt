package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "paire")
data class Paire(
    @PrimaryKey @NonNull val idPaire: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "choice_one_id") val choiceOneId: UUID?,
    @ColumnInfo(name = "choice_two_id") val choiceTwoId: UUID?,
    @ColumnInfo(name = "categoryId") var categoryId: UUID?,
)