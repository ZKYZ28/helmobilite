package com.example.projettupreferes.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation


data class PaireWithChoices(
    @Embedded val paire: Paire,
    @Relation(
        parentColumn = "choice_one_id",
        entityColumn = "idChoice"
    )
    val choiceOne: Choice?,
    @Relation(
        parentColumn = "choice_two_id",
        entityColumn = "idChoice"
    )
    val choiceTwo: Choice?
)
