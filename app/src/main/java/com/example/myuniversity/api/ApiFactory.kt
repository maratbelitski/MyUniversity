package com.example.myuniversity.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Belitski Marat
 * @date  20.11.2023
 * @project MyUniversity
 */

object ApiFactory {
    private const val BASE_URI = "http://universities.hipolabs.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URI)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}