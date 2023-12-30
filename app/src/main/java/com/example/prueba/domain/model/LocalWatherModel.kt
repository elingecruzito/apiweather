package com.example.prueba.domain.model

data class LocalWatherModel(
    val uid : Int? = null,
    val address: String? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val pressure: Long? = null,
    val humidity: Long? = null
)
