package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "category")
class Category(
    @PrimaryKey @NonNull val id: UUID,
    val categoryName : String
)