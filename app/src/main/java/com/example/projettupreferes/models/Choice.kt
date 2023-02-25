package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "choice")
data class Choice(
    @PrimaryKey @NonNull val idChoice: UUID = UUID.randomUUID(),
    val textChoice : String = "",
    val isText: Boolean,
)