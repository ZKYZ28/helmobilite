package com.example.projettupreferes.database

import androidx.room.TypeConverter
import com.example.projettupreferes.models.Paire
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PairListConverter {
//    @TypeConverter
//    fun fromString(value: String): MutableList<Paire> {
//        val pairs = mutableListOf<Paire>()
//        if (value.isNotEmpty()) {
//            val parts = value.split(";")
//            for (part in parts) {
//                val pair = PairConverter().fromString(part)
//                pairs.add(pair)
//            }
//        }
//        return pairs
//    }
//
//    @TypeConverter
//    fun fromPairList(pairs: MutableList<Paire>): String {
//        var value = ""
//        if (pairs.isNotEmpty()) {
//            for (pair in pairs) {
//                value += "${PairConverter().fromPair(pair)};"
//            }
//        }
//        return value
//    }

}


