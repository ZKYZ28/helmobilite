package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity (tableName = "statistics")
class Statistics(
    @PrimaryKey @NonNull val idStatistics: UUID = UUID.randomUUID(),
    @NonNull var gamesPlayed : Int,
    @NonNull var nbrCategories : Int,
    @NonNull var nbrSwipes : Int,
    @NonNull var nbrPairs : Int
)
