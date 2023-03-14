package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "category")
data class Category(
    @PrimaryKey @NonNull val idCategory: UUID = UUID.randomUUID(),
    var categoryName : String,
    var pathImage : String
)

