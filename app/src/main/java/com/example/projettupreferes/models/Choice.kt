package com.example.projettupreferes.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "choice",
    foreignKeys = [
        ForeignKey(
            entity = Paire::class,
            parentColumns = ["idPaire"],
            childColumns = ["pairIdFk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Choice(
    @PrimaryKey @NonNull val idChoice: UUID = UUID.randomUUID(),
    @NonNull val textChoice : String = "",
    @NonNull val isText: Boolean,
    @NonNull @ColumnInfo(name = "pairIdFk") var pairIdFk: UUID,
)