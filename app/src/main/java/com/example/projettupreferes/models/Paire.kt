package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "paire")
data class Paire(
    @PrimaryKey @NonNull val id: UUID = UUID.randomUUID(),
    @Embedded(prefix = "choice_one_") val choiceOne : Choice,
    @Embedded(prefix = "choice_two_") val choiceTwo : Choice,
    @ColumnInfo(name = "categoryId") var categoryId: UUID
)
