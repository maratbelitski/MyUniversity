package com.example.myuniversity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myuniversity.pojo.University

/**
 * @author Belitski Marat
 * @date  23.11.2023
 * @project MyUniversity
 */

@Database(entities = [University::class], version = 1, exportSchema = false)
abstract class UniversityDataBase(): RoomDatabase() {
    abstract fun getDao():UniversityDao
    companion object{
        fun getDB(context: Context): UniversityDataBase {
            return Room.databaseBuilder(context, UniversityDataBase::class.java,"MyDataBase").build()
        }
    }
}