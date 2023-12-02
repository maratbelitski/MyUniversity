package com.example.myuniversity.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myuniversity.database.UniversityConverter
import com.google.gson.annotations.SerializedName

/**
 * @author Belitski Marat
 * @date  20.11.2023
 * @project MyUniversity
 */

@Entity("favorite")
@TypeConverters(UniversityConverter::class)
data class University(

    @PrimaryKey()
    @SerializedName("name")
    val name: String,

    @SerializedName("isFavorite")
    var isFavorite: Boolean = false,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("domains")
    val domains: List<String>? = null,

    @SerializedName("web_pages")
    val webPages: List<String>
)