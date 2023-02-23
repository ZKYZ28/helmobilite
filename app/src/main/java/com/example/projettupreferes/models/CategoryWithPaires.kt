package com.example.projettupreferes.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithPaires(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val paires: List<Paire>
)
