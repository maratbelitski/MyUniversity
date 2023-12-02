package com.example.myuniversity.api

import com.example.myuniversity.pojo.ServerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Belitski Marat
 * @date  20.11.2023
 * @project MyUniversity
 */
interface ApiService {
    @GET("search?")
    fun loadUniversity(): Single<ServerResponse>?

    @GET("search?")
    fun loadCountryUniversity(@Query("country")  country:String): Single<ServerResponse>?
}