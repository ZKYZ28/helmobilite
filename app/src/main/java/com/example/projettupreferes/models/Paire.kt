package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "paire",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["idCategory"],
            childColumns = ["categoryIdFk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Paire(
    @PrimaryKey @NonNull val idPaire: UUID,
    @ColumnInfo(name = "choice_one_id") val choiceOneId: UUID,
    @ColumnInfo(name = "choice_two_id") val choiceTwoId: UUID,
    @ColumnInfo(name = "categoryIdFk") var categoryIdFk: UUID,
)