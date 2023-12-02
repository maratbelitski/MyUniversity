package com.example.myuniversity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myuniversity.pojo.University
import io.reactivex.rxjava3.core.Completable

/**
 * @author Belitski Marat
 * @date  23.11.2023
 * @project MyUniversity
 */
@Dao
interface UniversityDao {

    @Query("SELECT * FROM favorite")
    fun getFavoriteUniversity(): LiveData<List<University>>

    @Query("DELETE FROM favorite WHERE name =:name")
    fun removeUniversity(name:String):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUniversity(university: University): Completable

}