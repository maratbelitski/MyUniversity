package com.example.myuniversity.database

import androidx.room.TypeConverter
import java.util.stream.Collectors

/**
 * @author Belitski Marat
 * @date  24.11.2023
 * @project MyUniversity
 */
//ROOM не обрабатывает списки, нужен конвертер
class UniversityConverter {
    @TypeConverter
    fun fromList(dataIn: List<String?>): String? {
        return dataIn.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toLists(dataTo: String): List<String>? {
        return listOf(*dataTo.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
    }
}