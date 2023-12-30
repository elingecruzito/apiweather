package com.example.prueba.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.prueba.domain.model.WatherEntity

@Dao
interface WatherDao {
    @Insert
    fun insertCurrentWather(vararg wather: WatherEntity)
    @Query("SELECT * FROM wather")
    fun getAllCurrentWather(): List<WatherEntity>

}