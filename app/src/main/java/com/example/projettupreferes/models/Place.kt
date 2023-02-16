package com.example.projettupreferes.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
class Place {
    @PrimaryKey
    private var id: UUID
    var name: String
    private var date: Date

    init {
        id = UUID.randomUUID()
        name = ""
        date = Date()
    }

    fun getId(): UUID {
        return id
    }

    fun setId(id: UUID) {
        this.id = id
    }

    fun getDate(): Date {
        return date
    }

    fun setDate(date: Date) {
        this.date = date
    }
}
