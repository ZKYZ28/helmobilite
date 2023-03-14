package com.example.projettupreferes.database

import androidx.room.TypeConverter
import java.util.*

class TuPreferesTypeConverters {

    @TypeConverter
    fun fromUuid(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUuid(uuid: String?): UUID {
        return UUID.fromString(uuid)
    }
}
