package com.example.prueba.domain.model

import com.google.gson.annotations.SerializedName

data class CurrentWather (
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("main")
    val main: Main
)
data class Coord (
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)

data class Main (
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feelsLike")
    val feelsLike: Double,
    @SerializedName("tempMin")
    val tempMin: Double,
    @SerializedName("tempMax")
    val tempMax: Double,
    @SerializedName("pressure")
    val pressure: Long,
    @SerializedName("humidity")
    val humidity: Long
)
