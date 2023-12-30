package com.example.prueba.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.Inet4Address


@Entity(tableName = "wather")
data class WatherEntity(
    @PrimaryKey(autoGenerate = true) val uid : Int? = null,
    @ColumnInfo(name = "address") val address: String? = null,
    @ColumnInfo(name = "lon") val lon: Double? = null,
    @ColumnInfo(name = "lat") val lat: Double? = null,
    @ColumnInfo(name = "temp") val temp: Double? = null,
    @ColumnInfo(name = "feelsLike") val feelsLike: Double? = null,
    @ColumnInfo(name = "tempMin") val tempMin: Double? = null,
    @ColumnInfo(name = "tempMax") val tempMax: Double? = null,
    @ColumnInfo(name = "pressure") val pressure: Long? = null,
    @ColumnInfo(name = "humidity") val humidity: Long? = null
)
