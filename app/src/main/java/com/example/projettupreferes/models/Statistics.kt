package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.PrimaryKey
import java.util.*

class Statistics(
    @PrimaryKey @NonNull val idStatistics: UUID = UUID.randomUUID(),
    var gamesPlayed : Int,
    var nbrCategories : Int,
    var nbrSwipes : Int,
    var nbrPairs : Int
)
