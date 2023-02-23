package com.example.projettupreferes.database

import android.util.Log
import androidx.room.TypeConverter
import com.example.projettupreferes.models.Choice
import java.util.*
import java.lang.Boolean

class ChoiceConverter {
//    @TypeConverter
//    fun fromString(value: String): Choice {
//        val parts = value.split(":")
//        Log.d("Choice CONVERTER UUID", UUID.fromString(parts[0]).toString())
//        Log.d("Choice CONVERTER TEXTE", parts[1])
//        Log.d("Choice CONVERTER Booléen", Boolean.parseBoolean(parts[2]).toString())
//        return Choice(UUID.fromString(parts[0]), parts[1], Boolean.parseBoolean(parts[2]))
//    }
//
//    @TypeConverter
//    fun fromChoice(choice: Choice): String {
//        Log.d("Choice.id", choice.id.toString())
//        Log.d("Choice.textChoice", choice.textChoice)
//        Log.d("Choice.isText", choice.isText.toString())
//        return "${choice.id}:${choice.textChoice}:${choice.isText}"
//    }
}
