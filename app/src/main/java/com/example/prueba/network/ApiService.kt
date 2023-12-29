package com.example.prueba.network

import com.example.prueba.domain.model.CurrentWather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWather(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("appid") key : String) : CurrentWather

}