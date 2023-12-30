package com.example.prueba.domain.model

data class CurrentWatherModel (
    val coord: CoordModel,
    val main: MainModel,
    var address : String? = null
)
data class CoordModel (
    val lon: Double,
    val lat: Double
)

data class MainModel (
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long
)