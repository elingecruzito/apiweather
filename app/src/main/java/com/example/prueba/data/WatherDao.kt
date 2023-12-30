package com.example.prueba.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.prueba.domain.model.WatherEntity
@Dao
interface WatherDao {
    @Insert
    fun insertCurrentWather(vararg wather: WatherEntity)

}