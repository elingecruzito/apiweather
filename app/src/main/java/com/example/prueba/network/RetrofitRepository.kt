package com.example.prueba.network

interface RetrofitRepository {

    suspend fun getCurrentWather(lat: Float, lon: Float)

}