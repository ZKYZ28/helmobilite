package com.example.projettupreferes.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
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

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.getTime()
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}
